package com.PPOOII.Proyecto.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API CliniTech Solutions")
                .version("1.0.0")
                .description("Documentación técnica de la API de CliniTech para gestión de personas, usuarios y coordenadas.")
                .contact(new Contact()
                    .name("Daniel Camilo Amaya Rodríguez")
                    .email("dcamayar@ut.edu.co")));
    }
}