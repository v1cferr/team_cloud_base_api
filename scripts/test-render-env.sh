#!/bin/bash

# Script para testar a aplicação simulando o ambiente do Render.com

echo "🔧 Configurando ambiente para simular Render.com..."

# Verificar se PostgreSQL está rodando localmente
if ! command -v pg_isready &> /dev/null; then
    echo "❌ PostgreSQL não encontrado. Instalando/configurando..."
    
    # Para sistemas baseados em Ubuntu/Debian
    if command -v apt &> /dev/null; then
        sudo apt update
        sudo apt install -y postgresql postgresql-contrib
        sudo systemctl start postgresql
    fi
    
    # Para sistemas baseados em Arch
    if command -v pacman &> /dev/null; then
        sudo pacman -S postgresql
        sudo -u postgres initdb -D /var/lib/postgres/data
        sudo systemctl start postgresql
    fi
fi

# Configurar banco local para testes
echo "🗄️ Configurando banco de dados local..."

# Criar usuário e banco
sudo -u postgres createuser -s scenario 2>/dev/null || echo "Usuário 'scenario' já existe"
sudo -u postgres createdb local 2>/dev/null || echo "Banco 'local' já existe"
sudo -u postgres psql -c "ALTER USER scenario PASSWORD 'scenario';" 2>/dev/null

# Verificar conexão
if pg_isready -h localhost -p 5432; then
    echo "✅ PostgreSQL está rodando"
else
    echo "❌ Erro: PostgreSQL não está acessível"
    exit 1
fi

# Simular variáveis de ambiente do Render.com
export SPRING_PROFILES_ACTIVE=hobby
export DATABASE_URL="jdbc:postgresql://localhost:5432/local"
export PGUSER="scenario"
export PGPASSWORD="scenario"
export PGHOST="localhost"
export PGPORT="5432"
export PGDATABASE="local"

echo "🚀 Iniciando aplicação com perfil hobby..."
echo "📊 Variáveis de ambiente configuradas:"
echo "   SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE"
echo "   DATABASE_URL=$DATABASE_URL"

# Executar aplicação
if [ -f "target/test-0.0.1-SNAPSHOT.jar" ]; then
    echo "📦 Executando JAR local..."
    java -Xmx256m -Xms128m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 \
         -jar target/test-0.0.1-SNAPSHOT.jar
elif command -v docker &> /dev/null && docker images | grep -q "team-cloud-api"; then
    echo "🐳 Executando Docker com configuração de hobby..."
    docker run -p 8080:8080 \
        -e SPRING_PROFILES_ACTIVE=hobby \
        -e DATABASE_URL="jdbc:postgresql://host.docker.internal:5432/local" \
        -e PGUSER=scenario \
        -e PGPASSWORD=scenario \
        team-cloud-api:latest
else
    echo "❌ Nem JAR nem imagem Docker encontrados"
    echo "Execute primeiro: ./mvnw clean package -DskipTests"
    echo "Ou: ./build-docker.sh"
    exit 1
fi
