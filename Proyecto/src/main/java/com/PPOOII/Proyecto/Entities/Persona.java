package com.PPOOII.Proyecto.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "persona")
@Schema(
    name = "Persona",
    description = "Entidad que representa a una persona, con datos demográficos y relaciones a Usuario y Coordenadas"
)
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la persona (PK)", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Número de identificación único de la persona", example = "12345678")
    private int identificacion;

    @Column(nullable = false)
    @Schema(description = "Primer nombre de la persona", example = "Juan")
    private String pnombre;

    @Schema(description = "Segundo nombre de la persona (opcional)", example = "Carlos")
    private String snombre;

    @Column(nullable = false)
    @Schema(description = "Primer apellido de la persona", example = "Pérez")
    private String papellido;

    @Schema(description = "Segundo apellido de la persona (opcional)", example = "González")
    private String sapellido;

    @Column(nullable = false, unique = true)
    @Schema(description = "Correo electrónico único de la persona", example = "juan.perez@example.com")
    private String email;

    @Column(nullable = false)
    @Schema(
        description = "Fecha de nacimiento de la persona (formato yyyy-MM-dd)",
        example = "1990-04-15"
    )
    private LocalDate fechanacimiento;

    @Schema(description = "Edad calculada automáticamente (solo lectura)", example = "35")
    private int edad;

    @Schema(description = "Edad en formato clínico: “X años Y meses Z días” (solo lectura)", example = "35 años 1 mes 7 días")
    private String edadclinica;

    @Schema(description = "Ubicación libre (texto) de la persona, si se desea almacenar", example = "Av. Siempre Viva 742")
    @Column(length = 255)
    private String ubicacion;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("usuario-persona")
    @Schema(
        description = "Relación OneToOne a la entidad Usuario (se guarda el ‘login’, ‘password’ y ‘apikey’).",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Usuario usuario;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("actual-persona")
    @Schema(
        description = "Coordenadas actuales de la persona (OneToOne con la entidad Coordenadas).",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Coordenadas coordenadasActual;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    @JsonIgnore
    @Schema(
        description = "Lista de coordenadas históricas asociadas a la persona (OneToMany).",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private List<CoordenadasHistoricas> coordenadasHistoricas = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calcularEdad() {
        if (fechanacimiento != null) {
            LocalDate hoy = LocalDate.now();
            Period periodo = Period.between(fechanacimiento, hoy);
            this.edad = periodo.getYears();
            this.edadclinica = String.format(
                "%d años %d meses %d días",
                periodo.getYears(),
                periodo.getMonths(),
                periodo.getDays()
            );
        }
    }
}




