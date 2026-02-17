package com.PPOOII.Proyecto.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "coordenadas_historicas")
@Schema(
    name = "CoordenadasHistoricas",
    description = "Entidad que almacena el historial de coordenadas (latitud/longitud + timestamp) de una Persona"
)
public class CoordenadasHistoricas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la fila (PK)", example = "1")
    private Long id;

    @Column(name = "nombre_persona", nullable = false)
    @Schema(description = "Nombre de la persona a la que se relacionan estas coordenadas históricas", example = "Juan Pérez")
    private String nombrePersona;

    @Column(nullable = false)
    @Schema(description = "Latitud histórica en grados decimales", example = "-34.6037")
    private Double latitud;

    @Column(nullable = false)
    @Schema(description = "Longitud histórica en grados decimales", example = "-58.3816")
    private Double longitud;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    @Schema(
        description = "Fecha y hora en la que se registraron estas coordenadas (autogenerado)",
        example = "2025-05-22T14:30:00"
    )
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id", nullable = false)
    @JsonBackReference("historico-persona")
    @Schema(
        description = "La persona a quien pertenece este registro histórico de coordenadas",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Persona persona;

    @PrePersist
    public void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }

}

