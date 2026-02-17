package com.PPOOII.Proyecto.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coordenadas")
@Schema(
    name = "Coordenadas",
    description = "Entidad que almacena las coordenadas actuales de una Persona (latitud/longitud)"
)
public class Coordenadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la fila (PK)", example = "1")
    private Long id;

    @Column(name = "nombre_persona", nullable = false)
    @Schema(description = "Nombre de la persona a quien pertenecen estas coordenadas", example = "Juan Pérez")
    private String nombrePersona;

    @Column(nullable = false)
    @Schema(description = "Latitud en grados decimales", example = "-34.6037")
    private Double latitud;

    @Column(nullable = false)
    @Schema(description = "Longitud en grados decimales", example = "-58.3816")
    private Double longitud;

    @OneToOne
    @JoinColumn(name = "persona_id", nullable = false)
    @JsonBackReference("actual-persona")
    @Schema(
        description = "La persona a quien estas coordenadas pertenecen. Relación OneToOne (mappeado en Persona).",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Persona persona;

}


