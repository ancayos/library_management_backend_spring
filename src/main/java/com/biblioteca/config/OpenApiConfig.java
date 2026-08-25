package com.biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadatos generales del contrato OpenAPI.
 * La activacion/desactivacion real de la consola Swagger-UI se controla
 * exclusivamente por perfil en application-dev.yml / application-prod.yml.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bibliotecaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema de Gestion de Bibliotecas")
                        .description("Microservicio Hito 4: REST + PostgreSQL/Docker + OpenAPI")
                        .version("v1.0.0")
                        .contact(new Contact().name("Equipo Backend").email("backend@biblioteca.com"))
                        .license(new License().name("MIT")));
    }
}
