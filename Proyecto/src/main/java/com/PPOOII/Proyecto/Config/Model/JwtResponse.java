package com.PPOOII.Proyecto.Config.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(
    name = "JwtResponse",
    description = "Modelo de respuesta de autenticación. Devuelve el token JWT generado."
)
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;

    @Schema(
        description = "Token JWT en formato Bearer",
        example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyaXNhZG8iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjI5OTk5OTk5LCJleHAiOjE2MzAwMTA1OTl9.abc123xyz"
    )
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}

