package com.PPOOII.Proyecto.Config.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(
    name = "JwtRequest",
    description = "Modelo de petición para autenticación. Contiene el nombre de usuario y la contraseña."
)
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(
        description = "Nombre de usuario (login) para autenticación",
        example = "usuario123",
        required = true
    )
    private String username;

    @Schema(
        description = "Contraseña del usuario",
        example = "miContraseñaSecreta",
        required = true
    )
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
