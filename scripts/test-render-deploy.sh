#!/bin/bash

# Test script to verify Render.com deployment configuration
echo "🔍 Verificando configuração para deploy no Render.com..."

# Check if Dockerfile has the SPRING_PROFILES_ACTIVE
if grep -q "SPRING_PROFILES_ACTIVE=hobby" Dockerfile; then
    echo "✅ Dockerfile configurado corretamente com profile 'hobby'"
else
    echo "❌ ERRO: Dockerfile não possui SPRING_PROFILES_ACTIVE=hobby"
    exit 1
fi

# Check if hobby profile exists
if [[ -f "src/main/resources/application-hobby.properties" ]]; then
    echo "✅ Arquivo application-hobby.properties encontrado"
else
    echo "❌ ERRO: application-hobby.properties não encontrado"
    exit 1
fi

# Check if hobby profile has DATABASE_URL configuration
if grep -q "DATABASE_URL" src/main/resources/application-hobby.properties; then
    echo "✅ Configuração DATABASE_URL encontrada no profile hobby"
else
    echo "❌ ERRO: DATABASE_URL não configurada no profile hobby"
    exit 1
fi

# Test build
echo "🔨 Testando build da aplicação..."
if ./mvnw clean package -DskipTests -q; then
    echo "✅ Build da aplicação executado com sucesso"
else
    echo "❌ ERRO: Falha no build da aplicação"
    exit 1
fi

echo ""
echo "🎉 Configuração validada com sucesso!"
echo ""
echo "📋 Próximos passos para deploy no Render.com:"
echo "1. Faça commit e push dessas alterações"
echo "2. Crie um PostgreSQL Database no Render.com"
echo "3. Crie um Web Service no Render.com"
echo "4. Conecte o PostgreSQL ao Web Service"
echo "5. O deploy deve funcionar automaticamente"
echo ""
echo "🔗 Mais detalhes em: DEPLOY.md"
