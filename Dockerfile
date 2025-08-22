# Multi-stage build para reduzir o tamanho da imagem final

# Estágio 1: Build
FROM openjdk:17-jdk-slim AS builder

# Instalar Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN ./mvnw clean package -DskipTests -B

# Estágio 2: Runtime
FROM openjdk:17-jre-slim

# Criar usuário não-root para segurança
RUN addgroup --system spring && adduser --system spring --ingroup spring

# Definir diretório de trabalho
WORKDIR /app

# Copiar o JAR da aplicação do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Mudar ownership dos arquivos para o usuário spring
RUN chown spring:spring app.jar

# Trocar para usuário não-root
USER spring

# Variáveis de ambiente para produção - OTIMIZADO PARA HOBBY PLAN
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UseContainerSupport -Djava.awt.headless=true"
ENV SERVER_PORT=8080

# Expor a porta
EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
