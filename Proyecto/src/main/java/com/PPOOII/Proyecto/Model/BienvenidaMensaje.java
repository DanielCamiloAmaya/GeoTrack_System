package com.PPOOII.Proyecto.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(
    name = "BienvenidaMensaje",
    description = "Modelo que representa el mensaje de bienvenida con datos para el correo."
)
public class BienvenidaMensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "Dirección de correo electrónico a la que se envía el mensaje", example = "usuario@example.com")
    private String to;

    @Schema(description = "Login (usuario) que se creó", example = "juan.perez")
    private String login;

    @Schema(description = "Contraseña generada para el usuario", example = "abc123Secreto")
    private String password;

    @Schema(description = "API key asociada al usuario", example = "XYZ-987654321")
    private String apikey;

    public BienvenidaMensaje() {}

    public BienvenidaMensaje(String to, String login, String password, String apikey) {
        this.to = to;
        this.login = login;
        this.password = password;
        this.apikey = apikey;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
