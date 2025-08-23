# Configuração CORS específica para Vercel (opcional)

## Para restringir apenas ao seu domínio da Vercel

### 1. No SecurityConfig.java

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Permitir apenas seu domínio da Vercel
    configuration.setAllowedOriginPatterns(Arrays.asList(
        "https://*.vercel.app",
        "https://seu-app.vercel.app", // substitua pelo seu domínio
        "http://localhost:3000" // para desenvolvimento local
    ));
    
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### 2. No TestApplication.java

```java
@Bean
public WebMvcConfigurer corsConfig() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(@Nonnull CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins(
                        "https://*.vercel.app",
                        "https://seu-app.vercel.app", // substitua pelo seu domínio
                        "http://localhost:3000"
                    )
                    .allowedMethods("*");
        }
    };
}
```

## Mas por enquanto, a configuração atual com CORS aberto está perfeita para testes
