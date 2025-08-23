#!/bin/bash

# Script para testar a configuração com a URL exata do Render.com
echo "🧪 Testando configuração com URL real do Render.com..."

# URL exata do seu banco PostgreSQL no Render.com
DATABASE_URL="postgresql://team_cloud_db_user:KjfGcnxvS53bt4P5zakFqM3dJyzZvrZ8@dpg-d2jsjeu3jp1c73fgqhvg-a/team_cloud_db"

echo "📋 Informações do banco:"
echo "Database: team_cloud_db"
echo "Username: team_cloud_db_user" 
echo "Host: dpg-d2jsjeu3jp1c73fgqhvg-a"
echo "URL: $DATABASE_URL"
echo ""

# Criar um teste simples em Java para verificar parsing da URL
cat > /tmp/test_url_parsing.java << 'EOF'
import java.net.URI;

public class test_url_parsing {
    public static void main(String[] args) throws Exception {
        String databaseUrl = "postgresql://team_cloud_db_user:KjfGcnxvS53bt4P5zakFqM3dJyzZvrZ8@dpg-d2jsjeu3jp1c73fgqhvg-a/team_cloud_db";
        
        System.out.println("🔍 Testando parsing da URL:");
        System.out.println("Original: " + databaseUrl);
        
        URI uri = new URI(databaseUrl);
        
        System.out.println("Host: " + uri.getHost());
        System.out.println("Port: " + uri.getPort() + " (will use 5432 if -1)");
        System.out.println("Path: " + uri.getPath());
        System.out.println("UserInfo: " + uri.getUserInfo());
        
        // Simular nossa lógica
        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", uri.getHost(), port, uri.getPath());
        
        String[] userInfo = uri.getUserInfo().split(":");
        String username = userInfo[0];
        String password = userInfo.length > 1 ? userInfo[1] : "";
        
        System.out.println("");
        System.out.println("✅ URL convertida para JDBC:");
        System.out.println("JDBC URL: " + jdbcUrl);
        System.out.println("Username: " + username);
        System.out.println("Password: " + (password.length() > 0 ? "[HIDDEN]" : "[EMPTY]"));
    }
}
EOF

# Compilar e executar o teste
javac /tmp/test_url_parsing.java -d /tmp 2>/dev/null
java -cp /tmp test_url_parsing

echo ""
echo "🚀 Para aplicar no Render.com:"
echo "1. Faça commit e push das alterações"
echo "2. No Web Service → Settings → Environment, adicione:"
echo "   Key: DATABASE_URL"
echo "   Value: $DATABASE_URL"
echo ""
echo "3. Ou conecte automaticamente o PostgreSQL database 'team_cloud_db'"
