# Team Cloud Base API

Software back-end desenvolvido em Java + Spring Boot para simples gerenciamento de projetos de automação residencial.

## 🌐 Deploy em Produção

**API Online**: <https://team-cloud-base-api.onrender.com>

**Swagger UI**: <https://team-cloud-base-api.onrender.com/swagger-ui/index.html>

**Credenciais de acesso:**

- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

## 📋 Pré-requisitos

- **Java 17** ou superior
- **Maven** (ou usar o wrapper incluído `./mvnw`)
- **PostgreSQL** (ou Docker para rodar via container)
- **Git** para clonar o repositório

## 🚀 Configuração e Execução

### Opção 1: Usando Docker (Recomendado)

Esta é a forma mais rápida para testar a aplicação:

#### 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
cd team_cloud_base_api
```

#### 2. Inicie o PostgreSQL via Docker

```bash
# Inicia um container PostgreSQL com as configurações necessárias
docker run --name postgres-scenario \
  -e POSTGRES_DB=local \
  -e POSTGRES_USER=scenario \
  -e POSTGRES_PASSWORD=scenario \
  -p 5432:5432 \
  -d postgres:15

# Verificar se o container está rodando
docker ps
```

#### 3. Execute a aplicação

```bash
# Usando o Maven wrapper (recomendado)
./mvnw spring-boot:run

# Ou compile e execute o JAR
./mvnw clean package
java -jar target/test-0.0.1-SNAPSHOT.jar
```

### Opção 2: PostgreSQL Local

Se preferir instalar PostgreSQL diretamente na máquina:

#### Ubuntu/Debian

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

#### Arch Linux

```bash
sudo pacman -S postgresql
sudo -u postgres initdb -D /var/lib/postgres/data
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

#### macOS

```bash
brew install postgresql
brew services start postgresql
```

#### Windows

Baixe e instale do site oficial: <https://www.postgresql.org/download/windows/>

#### Configurar banco de dados

```bash
# Criar usuário e banco
sudo -u postgres createuser -s scenario
sudo -u postgres createdb local
sudo -u postgres psql -c "ALTER USER scenario PASSWORD 'scenario';"
```

## ⚙️ Configuração do Banco

As configurações padrão estão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/local
spring.datasource.username=scenario
spring.datasource.password=scenario
```

Se necessário, altere esses valores conforme sua configuração local.

## 🔧 Comandos Úteis

### Desenvolvimento

```bash
# Compilar o projeto
./mvnw clean compile

# Executar testes
./mvnw test

# Executar um teste específico
./mvnw test -Dtest=TestApplicationTests

# Empacotar sem executar testes
./mvnw clean package -DskipTests

# Executar com profile específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker (Gerenciamento do PostgreSQL)

```bash
# Parar o container
docker stop postgres-scenario

# Iniciar container existente
docker start postgres-scenario

# Remover container (dados serão perdidos)
docker rm postgres-scenario

# Ver logs do PostgreSQL
docker logs postgres-scenario

# Conectar ao PostgreSQL via psql
docker exec -it postgres-scenario psql -U scenario -d local
```

## 📡 Testando a API

### Swagger UI

Acesse a documentação interativa da API:

**Produção (Render.com):**

```url
https://team-cloud-base-api.onrender.com/swagger-ui/index.html
```

**Local (desenvolvimento):**

```url
http://localhost:8080/swagger-ui/index.html
```

### Endpoints disponíveis

**Base URL Produção**: `https://team-cloud-base-api.onrender.com`
**Base URL Local**: `http://localhost:8080`

- `GET /projects` - Listar todos os projetos
- `POST /projects` - Criar novo projeto
- `PUT /projects/{id}` - Atualizar projeto existente
- `DELETE /projects/{id}` - Deletar projeto

### Exemplos com cURL

**Produção (Render.com):**

```bash
# Listar projetos
curl -X GET https://team-cloud-base-api.onrender.com/api/projects

# Criar projeto (necessita autenticação)
curl -X POST https://team-cloud-base-api.onrender.com/api/projects \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{"name":"Projeto Teste"}'

# Atualizar projeto (substitua {id} pelo ID real)
curl -X PUT https://team-cloud-base-api.onrender.com/api/projects/1 \
  -u admin:admin123 \
  -H "Content-Type: application/json" \
  -d '{"name":"Projeto Atualizado"}'

# Deletar projeto
curl -X DELETE https://team-cloud-base-api.onrender.com/api/projects/1 \
  -u admin:admin123
```

**Local (desenvolvimento):**

```bash
# Listar projetos
curl -X GET http://localhost:8080/projects

# Criar projeto
curl -X POST http://localhost:8080/projects \
  -H "Content-Type: application/json" \
  -d '{"name":"Projeto Teste"}'

# Atualizar projeto (substitua {id} pelo ID real)
curl -X PUT http://localhost:8080/projects/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Projeto Atualizado"}'

# Deletar projeto
curl -X DELETE http://localhost:8080/projects/1
```

### 🔑 Credenciais de Acesso (Produção)

- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

## 📦 Estrutura do Projeto

```bash
src/
├── main/
│   ├── java/com/scenario/team/cloud/test/
│   │   ├── TestApplication.java          # Aplicação principal
│   │   └── projects/
│   │       ├── ProjectController.java    # Endpoints REST
│   │       ├── ProjectService.java       # Lógica de negócio
│   │       ├── ProjectRepository.java    # Acesso a dados
│   │       ├── domain/
│   │       │   └── Project.java          # Entidade JPA
│   │       ├── dto/
│   │       │   ├── InProjectDTO.java     # DTO entrada
│   │       │   └── OutProjectDTO.java    # DTO saída
│   │       └── exceptions/
│   │           └── ProjectNotFoundException.java
│   └── resources/
│       └── application.properties        # Configurações
└── test/
    └── java/
        └── TestApplicationTests.java     # Testes
```

## 🐛 Solução de Problemas

### Erro de conexão com banco

- Verifique se PostgreSQL está rodando: `docker ps` ou `systemctl status postgresql`
- Confirme as credenciais em `application.properties`
- Teste a conexão: `psql -h localhost -U scenario -d local`

### Porta já em uso

Se a porta 8080 estiver ocupada:

```bash
# Encontrar processo usando a porta
sudo lsof -i :8080

# Matar processo (substitua PID)
sudo kill -9 <PID>

# Ou usar porta diferente
./mvnw spring-boot:run -Dserver.port=8081
```

### Problemas com Java

```bash
# Verificar versão do Java
java -version

# Verificar JAVA_HOME
echo $JAVA_HOME
```

## 📝 Notas Importantes

- A aplicação cria automaticamente as tabelas no banco (DDL auto)
- Os dados são persistidos enquanto o container PostgreSQL estiver ativo
- Para ambiente de produção, considere usar configurações mais seguras
- O CORS está configurado para aceitar qualquer origem (desenvolvimento apenas)

## 🔗 Links Úteis

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)
