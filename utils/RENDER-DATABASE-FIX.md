# Fix para Deploy no Render.com - DATABASE_URL Issue

## Problema Identificado

A aplicação estava falhando no deploy com o erro:
```
Connection to localhost:5432 refused
```

## Causa Raiz

A aplicação não estava usando o profile Spring correto (`hobby`), fazendo com que ela tentasse conectar no banco local (`localhost:5432`) em vez do PostgreSQL do Render.com.

## Solução Implementada

### 1. Atualização do Dockerfile

**Antes:**
```dockerfile
ENV JAVA_OPTS="-Xmx256m -Xms128m ..."
ENV SERVER_PORT=8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
```

**Depois:**
```dockerfile
ENV JAVA_OPTS="-Xmx256m -Xms128m ..."
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=hobby

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
```

### 2. Configuração do Profile Hobby

O arquivo `application-hobby.properties` já estava configurado corretamente:

```properties
# Configuração do banco de dados PostgreSQL
# Render.com fornece DATABASE_URL automaticamente
spring.datasource.url=${DATABASE_URL:jdbc:postgresql://${PGHOST:localhost}:${PGPORT:5432}/${PGDATABASE:local}}
spring.datasource.username=${PGUSER:scenario}
spring.datasource.password=${PGPASSWORD:scenario}
```

### 3. Script de Validação

Criado `test-render-deploy.sh` para validar a configuração antes do deploy.

## Como Funciona

1. **Profile Ativo**: O Dockerfile agora força o uso do profile `hobby`
2. **DATABASE_URL**: O Render.com fornece automaticamente esta variável
3. **Fallback**: Se DATABASE_URL não existir, usa as variáveis individuais (PGHOST, PGPORT, etc.)

## Verificação

Execute o script de validação:
```bash
./test-render-deploy.sh
```

## Deploy no Render.com

1. **Faça commit e push** das alterações
2. **Crie um PostgreSQL Database** no Render.com
3. **Crie um Web Service** no Render.com
4. **Conecte o PostgreSQL ao Web Service** (isso adiciona a DATABASE_URL automaticamente)
5. O deploy deve funcionar automaticamente

## Resultado Esperado

Com essas alterações, a aplicação deve:
- ✅ Usar o profile `hobby` corretamente
- ✅ Conectar no PostgreSQL do Render.com via DATABASE_URL
- ✅ Inicializar sem erros de conexão
- ✅ Responder no health check: `/actuator/health`

## Configurações Importantes no Render.com

### Web Service
- **Environment**: Docker
- **Health Check Path**: `/actuator/health`
- **Port**: 8080

### Environment Variables
O Render.com adiciona automaticamente:
- `DATABASE_URL` (quando você conecta o PostgreSQL)
- `PORT` (sempre 8080 no container)

### PostgreSQL Database
- Crie primeiro o banco de dados
- Depois conecte ao Web Service
- Render.com faz a configuração automaticamente

## Troubleshooting

Se ainda houver problemas:

1. **Verifique os logs** no dashboard do Render.com
2. **Confirme o profile ativo** nos logs de startup
3. **Verifique a DATABASE_URL** nas variáveis de ambiente do Web Service
4. **Teste o health check** após o deploy: `https://seu-app.onrender.com/actuator/health`
