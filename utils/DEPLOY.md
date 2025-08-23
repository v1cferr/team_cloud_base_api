# Deploy no Render.com

## Arquivos criados para o deploy

- `Dockerfile` - Configuração do container Docker (Eclipse Temurin JDK 17)
- `.dockerignore` - Arquivos a serem ignorados no build
- `application-prod.properties` - Configurações para produção
- `application-hobby.properties` - Configurações otimizadas para Hobby Plan

## Otimizações para Render.com Hobby Plan

### ✅ Viabilidade

**SIM, é possível rodar no Hobby Plan** (512MB RAM / 0.1 CPU) com as seguintes otimizações:

### 🎯 Configurações aplicadas

- **Heap JVM**: Reduzido para 256MB máximo (deixa ~256MB para SO/containers)
- **Pool de conexões**: Limitado a 2 conexões máximas
- **Threads Tomcat**: Reduzido para 20 threads máximas
- **Lazy initialization**: Habilitado para reduzir tempo de startup
- **Logging**: Minimizado para reduzir overhead
- **GC**: G1GC otimizado para baixa latência

### ⚡ Performance esperada

- **Startup**: ~60-90 segundos
- **Resposta API**: 200-500ms para operações simples
- **Throughput**: ~10-20 requisições simultâneas
- **Adequado para**: Desenvolvimento, testes, demos, APIs de baixo tráfego

### 📊 Monitoramento

Use apenas `/actuator/health` para evitar overhead de métricas desnecessárias.

## Configuração no Render.com

### 1. Criar um Web Service

1. Acesse [render.com](https://render.com) e faça login
2. Clique em "New" → "Web Service"
3. Conecte seu repositório GitHub/GitLab
4. Selecione este repositório

### 2. Configurações do Web Service

**Build & Deploy:**

- **Environment**: Docker
- **Build Command**: (deixe vazio, será usado o Dockerfile)
- **Start Command**: (deixe vazio, será usado o ENTRYPOINT do Dockerfile)

**Advanced:**

- **Health Check Path**: `/actuator/health`
- **Port**: `8080` (já configurado no Dockerfile)

### 3. Configurar PostgreSQL no Render.com

**PRIMEIRO: Criar o PostgreSQL Database**
1. No dashboard do Render.com, clique em "New" → "PostgreSQL"
2. Dê um nome ao banco (ex: `team-cloud-db`)
3. Escolha a região (preferencialmente a mesma do Web Service)
4. Clique em "Create Database"

### 4. Variáveis de Ambiente

**OPÇÃO 1: Automática (Recomendada)**
- O Render.com conecta automaticamente o PostgreSQL ao Web Service
- A variável `DATABASE_URL` é fornecida automaticamente
- Configure apenas:

```env
SPRING_PROFILES_ACTIVE=hobby
```

**OPÇÃO 2: Manual (se automática não funcionar)**
Vá na página do PostgreSQL criado, copie as informações e configure:

```env
SPRING_PROFILES_ACTIVE=hobby
DATABASE_URL=postgresql://username:password@hostname:port/database
```

**Para teste local:**
```env
SPRING_PROFILES_ACTIVE=hobby
DATABASE_URL=jdbc:postgresql://localhost:5432/local
PGUSER=scenario
PGPASSWORD=scenario
```

### 5. Configurar o Web Service

1. No dashboard do Render.com, clique em "New" → "Web Service"
2. Conecte seu repositório GitHub/GitLab
3. Selecione este repositório

**Build & Deploy:**
- **Environment**: Docker
- **Build Command**: (deixe vazio, será usado o Dockerfile)
- **Start Command**: (deixe vazio, será usado o ENTRYPOINT do Dockerfile)

**Advanced:**
- **Health Check Path**: `/actuator/health`
- **Port**: `8080` (já configurado no Dockerfile)

**Environment Variables:**
- Configure `SPRING_PROFILES_ACTIVE=hobby`
- O `DATABASE_URL` será adicionado automaticamente ao conectar o PostgreSQL

### 5. Deploy

1. Faça commit e push das alterações para o repositório
2. O Render.com automaticamente detectará as mudanças e iniciará o deploy
3. Aguarde o build e deploy completarem

## Endpoints importantes

- **Health Check**: `https://seu-app.onrender.com/actuator/health`
- **API Info**: `https://seu-app.onrender.com/actuator/info`
- **Swagger UI**: `https://seu-app.onrender.com/swagger-ui.html`

## Monitoramento

A aplicação inclui Spring Boot Actuator para monitoramento:

- `/actuator/health` - Status da aplicação
- `/actuator/metrics` - Métricas da aplicação
- `/actuator/info` - Informações da aplicação

## Troubleshooting

### ❌ "Connection to localhost:5432 refused"

**Problema**: A aplicação está tentando conectar no PostgreSQL local em vez do Render.com

**Soluções**:

1. **PRINCIPAL: Verificar o Spring Profile**:
   - O `SPRING_PROFILES_ACTIVE=hobby` deve estar configurado no Dockerfile (já está)
   - O profile `hobby` faz a aplicação usar as configurações corretas do banco

2. **Verificar variável de ambiente**:
   - Confirme que `SPRING_PROFILES_ACTIVE=hobby` está no Dockerfile
   - Verifique se o PostgreSQL está conectado ao Web Service

3. **Conectar PostgreSQL ao Web Service**:
   - Vá no Web Service → Settings → Environment
   - Clique em "Add Environment Variable"
   - Na seção "Add from Database", selecione seu PostgreSQL
   - Isso adiciona automaticamente `DATABASE_URL`

4. **Verificar ordem de criação**:
   - Crie PRIMEIRO o PostgreSQL Database
   - DEPOIS crie o Web Service
   - Conecte os dois na configuração

4. **Teste local**:
   ```bash
   # Execute para testar localmente
   ./test-render-env.sh
   ```

### ❌ Build falha

- Verifique se o Dockerfile está correto
- Confirme se todas as dependências estão no pom.xml

### Aplicação não inicia

- Verifique as variáveis de ambiente
- Confirme a configuração do banco de dados
- Verifique os logs no dashboard do Render.com

### Problemas de conectividade com banco

- Confirme se as credenciais do PostgreSQL estão corretas
- Verifique se o banco de dados está rodando
- Confirme se a URL de conexão está correta

## Configurações Adicionais

### Custom Domain

No dashboard do Render.com, vá em Settings → Custom Domains para configurar seu próprio domínio.

### Auto-Deploy

O auto-deploy está habilitado por padrão. Qualquer push para a branch principal disparará um novo deploy.

### Scaling

O Render.com permite escalar horizontalmente. Configure nas Settings do seu Web Service.
