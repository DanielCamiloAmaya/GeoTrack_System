package com.PPOOII.Proyecto.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuario")
@Schema(
    name = "Usuario",
    description = "Entidad que representa credenciales de acceso (login, password, apikey) asociada a una Persona"
)
public class Usuario {

    @Id
    @Schema(description = "ID de la persona con la que se vincula este Usuario (PK y FK)", example = "1")
    private Long idpersona;

    @Column(unique = true, nullable = false, updatable = false)
    @Schema(
        description = "Nombre de usuario (login), único e inmutable tras crearse",
        example = "juan.perez"
    )
    private String login;

    @Column(nullable = false)
    @Schema(description = "Contraseña en texto plano (en este ejemplo). En producción, debería guardarse en hash.", example = "abc123")
    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    @Schema(
        description = "API key única asociada al usuario, inmutable tras crearse",
        example = "XYZ-987654321"
    )
    private String apikey;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idpersona")
    @JsonBackReference("usuario-persona")
    @Schema(
        description = "La persona a la que pertenece este usuario",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Persona persona;

}

