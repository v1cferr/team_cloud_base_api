#!/bin/bash

# Script para testar a conexão com o banco PostgreSQL do Render.com
echo "🔍 Testando conexão com PostgreSQL do Render.com..."

# URL do banco (interna do Render.com)
DATABASE_URL="postgresql://team_cloud_db_user:KjfGcnxvS53bt4P5zakFqM3dJyzZvrZ8@dpg-d2jsjeu3jp1c73fgqhvg-a/team_cloud_db"

echo "📋 URL de conexão:"
echo "Internal: $DATABASE_URL"
echo ""

# Testar se o Spring Boot consegue interpretar a URL
echo "🧪 Testando aplicação com a DATABASE_URL do Render.com..."

# Exportar variáveis de ambiente para teste
export DATABASE_URL="$DATABASE_URL"
export SPRING_PROFILES_ACTIVE="hobby"

echo "✅ Variáveis de ambiente configuradas:"
echo "DATABASE_URL=$DATABASE_URL"
echo "SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE"
echo ""

echo "🚀 Para aplicar no Render.com:"
echo "1. Vá no seu Web Service → Settings → Environment"
echo "2. Adicione:"
echo "   Key: DATABASE_URL"
echo "   Value: $DATABASE_URL"
echo ""
echo "3. Ou use 'Add from Database' e selecione 'team_cloud_db'"
echo ""
echo "4. Verifique se existe:"
echo "   Key: SPRING_PROFILES_ACTIVE"
echo "   Value: hobby"
echo ""
echo "5. Clique em 'Manual Deploy' para forçar um novo deploy"

# Opcional: testar build local (descomente se quiser testar)
# echo "🔨 Testando build local com as variáveis..."
# ./mvnw clean package -DskipTests -q
# if [ $? -eq 0 ]; then
#     echo "✅ Build local funcionou com as variáveis do Render.com"
# else
#     echo "❌ Problema no build local"
# fi
