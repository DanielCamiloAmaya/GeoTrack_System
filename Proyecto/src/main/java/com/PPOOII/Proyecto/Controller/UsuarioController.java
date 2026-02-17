package com.PPOOII.Proyecto.Controller;

import com.PPOOII.Proyecto.Services.Interfaces.IUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario", description = "Operaciones sobre la entidad Usuario")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{idpersona}/password")
    @Operation(
        summary = "Cambiar contraseña de usuario",
        description = "Actualiza la contraseña (`nuevoPassword`) del usuario cuya persona tiene el ID dado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Contraseña actualizada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "Contraseña actualizada correctamente.")
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida: falta o está vacío el campo `nuevoPassword`",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(type = "string", example = "El campo 'nuevoPassword' es obligatorio.")
            )
        ),
        @ApiResponse(responseCode = "404", description = "No existe usuario con el ID de persona proporcionado")
    })
    public ResponseEntity<String> cambiarPassword(
        @Parameter(
            name = "idpersona",
            description = "ID de la persona cuyo usuario cambiará de contraseña",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
        )
        @PathVariable Long idpersona,

        @RequestBody(
            description = "Mapa JSON que debe contener clave `nuevoPassword`. Ejemplo: { \"nuevoPassword\": \"MiNuevaContraseña123\" }",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "object",
                    example = "{ \"nuevoPassword\": \"MiNuevaContraseña123\" }",
                    description = "Debe incluir la clave `nuevoPassword` con la nueva contraseña"
                )
            )
        )
        @org.springframework.web.bind.annotation.RequestBody Map<String, String> request
    ) {
        // Validar que el cuerpo de la petición tenga la clave "nuevoPassword"
        if (!request.containsKey("nuevoPassword") || request.get("nuevoPassword").isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'nuevoPassword' es obligatorio.");
        }

        String nuevoPassword = request.get("nuevoPassword");
        boolean resultado = usuarioService.cambiarPassword(idpersona, nuevoPassword);

        if (resultado) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{idpersona}/credenciales")
    @Operation(
        summary = "Obtener credenciales de usuario",
        description = "Retorna un JSON con las credenciales (`login`, `password` y `apikey`) del usuario cuya persona tiene el ID proporcionado."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Credenciales encontradas",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    type = "object",
                    example = "{ \"login\": \"juan.perez\", \"password\": \"abc123\", \"apikey\": \"XYZ-987654321\" }",
                    description = "Objeto con las tres claves: login, password y apikey"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "No se encontró usuario con el ID de persona proporcionado")
    })
    public ResponseEntity<Map<String, Object>> obtenerCredenciales(
        @Parameter(
            name = "idpersona",
            description = "ID de la persona de la cual se desean obtener las credenciales",
            required = true,
            in = ParameterIn.PATH,
            example = "42"
        )
        @PathVariable Long idpersona
    ) {
        Map<String, Object> credenciales = usuarioService.obtenerUsuarioPasswordApikey(idpersona);
        if (credenciales != null) {
            return ResponseEntity.ok(credenciales);
        }
        return ResponseEntity.notFound().build();
    }

}
