package com.clinica.api.config.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    /**
     * Configura o Springdoc OpenAPI para adicionar globalmente a opção
     * de autenticação via Token JWT (Bearer Auth) na interface do Swagger.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Adiciona o requisito de segurança globalmente em todos os endpoints
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components()
                        // Define o esquema de segurança do tipo HTTP Bearer com formato JWT
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .name("BearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ));
    }
}
