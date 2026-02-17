package com.PPOOII.Proyecto.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI (Swagger) que:
 *  1) Define un esquema API Key en el header "APIKey" llamado "ApiKeyAuth".
 *  2) Define un esquema Bearer JWT en el header "Authorization" llamado "BearerAuth".
 *  3) Aplica ambos esquemas de seguridad de forma global a todos los endpoints.
 */
@OpenAPIDefinition(
    info = @Info(title = "Proyecto CliniTech API", version = "v1"),
    security = {
        @SecurityRequirement(name = "ApiKeyAuth"),
        @SecurityRequirement(name = "BearerAuth")
    }
)
@SecurityScheme(
    name = "ApiKeyAuth",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER,
    paramName = "APIKey",
    description = "Ingrese su API Key aquí"
)
@SecurityScheme(
    name = "BearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "Ingrese 'Bearer <token_jwt>' aquí"
)
@Configuration
public class OpenApiSecurityConfig {
    // Solo con estas anotaciones, Swagger UI:
    //  - Al cargar mostrará el botón “Authorize”.
    //  - Permitirá ingresar la APIKey y el Bearer token.
    //  - Y luego los incluirá automáticamente como headers en todas las llamadas “Try it out”.
}
