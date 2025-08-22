package com.scenario.team.cloud.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Configuração específica para banco de dados no ambiente Render.com
 */
@Configuration
@Profile({"hobby", "prod"})
public class DatabaseConfig {

    @Value("${DATABASE_URL:}")
    private String databaseUrl;

    /**
     * Configura o DataSource a partir da DATABASE_URL fornecida pelo Render.com
     * Converte o formato postgres:// para jdbc:postgresql:// se necessário
     */
    @Bean
    @ConditionalOnProperty(name = "DATABASE_URL")
    public DataSource dataSource() throws URISyntaxException {
        if (!StringUtils.hasText(databaseUrl)) {
            throw new IllegalStateException("DATABASE_URL não foi fornecida pelo Render.com");
        }

        // Se a URL já estiver no formato correto, use-a diretamente
        if (databaseUrl.startsWith("jdbc:postgresql://")) {
            return DataSourceBuilder.create()
                    .url(databaseUrl)
                    .build();
        }

        // Converte postgres:// para jdbc:postgresql://
        if (databaseUrl.startsWith("postgres://")) {
            URI uri = new URI(databaseUrl);
            String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s",
                    uri.getHost(),
                    uri.getPort(),
                    uri.getPath());

            return DataSourceBuilder.create()
                    .url(jdbcUrl)
                    .username(uri.getUserInfo().split(":")[0])
                    .password(uri.getUserInfo().split(":")[1])
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }

        throw new IllegalStateException("Formato de DATABASE_URL não suportado: " + databaseUrl);
    }
}
