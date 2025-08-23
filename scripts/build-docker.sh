#!/bin/bash

# Script para build e teste do Docker

echo "🐳 Iniciando build do Docker..."
echo "📦 Usando Eclipse Temurin (recomendado para produção)"

# Build da imagem
docker build -t team-cloud-api:latest .

if [ $? -eq 0 ]; then
    echo "✅ Build concluído com sucesso!"
    
    echo ""
    echo "🚀 Para testar localmente:"
    echo "# Perfil hobby (512MB RAM):"
    echo "docker run -p 8080:8080 --env SPRING_PROFILES_ACTIVE=hobby team-cloud-api:latest"
    echo ""
    echo "# Perfil produção (1GB+ RAM):"
    echo "docker run -p 8080:8080 --env SPRING_PROFILES_ACTIVE=prod team-cloud-api:latest"
    
    echo ""
    echo "📊 Informações da imagem:"
    docker images team-cloud-api:latest
    
    echo ""
    echo "🔍 Testando health check:"
    echo "Aguarde ~60 segundos após iniciar e acesse: http://localhost:8080/actuator/health"
    
else
    echo "❌ Build falhou!"
    exit 1
fi
