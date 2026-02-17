package com.PPOOII.Proyecto.Controller;

import com.PPOOII.Proyecto.Entities.Persona;
import com.PPOOII.Proyecto.Services.Interfaces.IPersonaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona")
@Tag(name = "Persona", description = "Operaciones CRUD para la entidad Persona")
public class PersonaController {

    private final IPersonaService personaService;

    public PersonaController(IPersonaService personaService) {
        this.personaService = personaService;
    }

    @PostMapping
    @Operation(
        summary = "Crear Persona",
        description = "Registra una nueva persona en el sistema y retorna el objeto creado"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Persona creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes")
    })
    public ResponseEntity<Persona> crearPersona(
        @RequestBody(
            description = "Objeto JSON con los datos de la persona a crear",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        )
        @org.springframework.web.bind.annotation.RequestBody Persona persona
    ) {
        Persona creada = personaService.crearPersona(persona);
        return ResponseEntity.ok(creada);
    }

    @PutMapping
    @Operation(
        summary = "Actualizar Persona",
        description = "Actualiza la información de una persona existente. El ID no puede ser nulo."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Persona actualizada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "El ID de la persona es nulo"),
        @ApiResponse(responseCode = "404", description = "No se encontró una persona con el ID proporcionado")
    })
    public ResponseEntity<?> actualizarPersona(
        @RequestBody(
            description = "Objeto JSON con datos actualizados (debe incluir ID)",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        )
        @org.springframework.web.bind.annotation.RequestBody Persona persona
    ) {
        if (persona.getId() == null) {
            return ResponseEntity.badRequest().body("Error: El ID no puede ser nulo.");
        }

        Persona actualizada = personaService.actualizarPersona(persona);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Error: No se encontró una persona con el ID proporcionado.");
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar Persona",
        description = "Elimina la persona cuyo ID se provee como parámetro"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Persona eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró una persona con el ID proporcionado")
    })
    public ResponseEntity<?> eliminarPersona(
        @Parameter(
            name = "id",
            description = "ID de la persona a eliminar",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable Long id
    ) {
        boolean eliminado = personaService.eliminarPersona(id);
        if (eliminado) {
            return ResponseEntity.ok("Persona eliminada");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(
        summary = "Listar Personas",
        description = "Retorna la lista completa de personas registradas"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de personas",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Persona.class, type = "array")
        )
    )
    public List<Persona> listarPersonas() {
        return personaService.listarPersonas();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar Persona por ID",
        description = "Obtiene una persona según su ID"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Persona encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "No se encontró una persona con el ID proporcionado")
    })
    public ResponseEntity<Persona> buscarPorId(
        @Parameter(
            name = "id",
            description = "ID de la persona a buscar",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable Long id
    ) {
        Persona persona = personaService.buscarPorId(id);
        if (persona != null) {
            return ResponseEntity.ok(persona);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/identificacion/{identificacion}")
    @Operation(
        summary = "Buscar Persona por Identificación",
        description = "Obtiene una persona según su número de identificación"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Persona encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Persona.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "No se encontró una persona con la identificación proporcionada")
    })
    public ResponseEntity<Persona> buscarPorIdentificacion(
        @Parameter(
            name = "identificacion",
            description = "Número de identificación de la persona",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable int identificacion
    ) {
        Persona persona = personaService.buscarPorIdentificacion(identificacion);
        if (persona != null) {
            return ResponseEntity.ok(persona);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/edad/{edad}")
    @Operation(
        summary = "Buscar Personas por Edad",
        description = "Lista todas las personas que tengan la edad proporcionada"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de personas con la edad indicada",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Persona.class, type = "array")
        )
    )
    public List<Persona> buscarPorEdad(
        @Parameter(
            name = "edad",
            description = "Edad de las personas a buscar",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable int edad
    ) {
        return personaService.buscarPorEdad(edad);
    }

    @GetMapping("/apellido/{papellido}")
    @Operation(
        summary = "Buscar Personas por Primer Apellido",
        description = "Lista todas las personas cuyo primer apellido coincida con el parámetro"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de personas con el primer apellido indicado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Persona.class, type = "array")
        )
    )
    public List<Persona> buscarPorApellido(
        @Parameter(
            name = "papellido",
            description = "Primer apellido para filtrar personas",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable String papellido
    ) {
        return personaService.buscarPorPrimerApellido(papellido);
    }

    @GetMapping("/nombre/{pnombre}")
    @Operation(
        summary = "Buscar Personas por Primer Nombre",
        description = "Lista todas las personas cuyo primer nombre coincida con el parámetro"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de personas con el primer nombre indicado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Persona.class, type = "array")
        )
    )
    public List<Persona> buscarPorNombre(
        @Parameter(
            name = "pnombre",
            description = "Primer nombre para filtrar personas",
            required = true,
            in = ParameterIn.PATH
        )
        @PathVariable String pnombre
    ) {
        return personaService.buscarPorPrimerNombre(pnombre);
    }
}
