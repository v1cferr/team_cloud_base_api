package com.scenario.team.cloud.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Configuração específica para ambiente Render.com
 * Converte automaticamente URLs postgres:// para jdbc:postgresql://
 */
@Configuration
@Profile({"hobby", "prod"})
public class RenderDatabaseConfig {

    @Bean
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (!StringUtils.hasText(databaseUrl)) {
            // Se não houver DATABASE_URL, usar configuração padrão do Spring Boot
            return DataSourceBuilder.create().build();
        }

        try {
            // Se já estiver no formato correto, usar diretamente
            if (databaseUrl.startsWith("jdbc:postgresql://")) {
                return DataSourceBuilder.create()
                        .url(databaseUrl)
                        .build();
            }

            // Converter postgres:// para jdbc:postgresql://
            if (databaseUrl.startsWith("postgres://")) {
                URI uri = new URI(databaseUrl);
                
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s",
                        uri.getHost(),
                        uri.getPort() == -1 ? 5432 : uri.getPort(),
                        uri.getPath());

                String[] userInfo = uri.getUserInfo().split(":");
                String username = userInfo[0];
                String password = userInfo.length > 1 ? userInfo[1] : "";

                return DataSourceBuilder.create()
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .driverClassName("org.postgresql.Driver")
                        .build();
            }

            throw new IllegalStateException("Formato de DATABASE_URL não suportado: " + databaseUrl);
            
        } catch (URISyntaxException e) {
            throw new IllegalStateException("DATABASE_URL inválida: " + databaseUrl, e);
        }
    }
}
