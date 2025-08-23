#!/bin/bash

# Script para testar o consumo de memória da aplicação

echo "🔍 Testando configurações de memória para Hobby Plan..."

# Função para testar configuração
test_memory_config() {
    local profile=$1
    local max_heap=$2
    local min_heap=$3
    
    echo ""
    echo "🧪 Testando perfil: $profile"
    echo "   Heap máximo: $max_heap"
    echo "   Heap mínimo: $min_heap"
    
    # Simular JVM args
    local jvm_args="-Xmx$max_heap -Xms$min_heap -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
    echo "   JVM Args: $jvm_args"
    
    # Calcular overhead aproximado
    local heap_mb=$(echo $max_heap | sed 's/m//')
    local overhead_mb=$((heap_mb / 4))  # ~25% overhead estimado
    local total_mb=$((heap_mb + overhead_mb + 50))  # +50MB para SO
    
    echo "   Memória estimada total: ${total_mb}MB"
    
    if [ $total_mb -le 512 ]; then
        echo "   ✅ ADEQUADO para Hobby Plan (512MB)"
    else
        echo "   ❌ EXCEDE limite do Hobby Plan (512MB)"
    fi
}

echo "📊 Testando diferentes configurações:"

test_memory_config "hobby" "256m" "128m"
test_memory_config "prod" "512m" "256m"
test_memory_config "minimal" "200m" "100m"
test_memory_config "aggressive" "384m" "192m"

echo ""
echo "🎯 Recomendação:"
echo "   Para Hobby Plan: Use perfil 'hobby' (256m heap)"
echo "   Para planos superiores: Use perfil 'prod' (512m heap)"
echo ""
echo "💡 Dicas para otimização:"
echo "   1. Use SPRING_PROFILES_ACTIVE=hobby no Render.com"
echo "   2. Configure JAVA_OPTS no ambiente"
echo "   3. Monitore /actuator/health após deploy"
echo "   4. Considere lazy loading para reduzir startup"

echo ""
echo "🚀 Para testar localmente:"
echo "   docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=hobby team-cloud-api:latest"
