# Multi-stage build para reduzir o tamanho da imagem final

# Estágio 1: Build
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

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
FROM eclipse-temurin:17-jre-alpine

# Criar usuário não-root para segurança
RUN addgroup -g 1001 -S spring && adduser -u 1001 -S spring -G spring

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
ENV SERVER_PORT=10000
ENV SPRING_PROFILES_ACTIVE=hobby

# Expor a porta que o Render.com espera
EXPOSE 10000

# Comando para executar a aplicação com configurações otimizadas para Render.com
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Dserver.address=0.0.0.0 -Dserver.port=10000 -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
