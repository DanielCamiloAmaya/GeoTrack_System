package com.PPOOII.Proyecto.Controller;

import com.PPOOII.Proyecto.Config.JWTAuthtenticationConfig;
import com.PPOOII.Proyecto.Config.Model.JwtRequest;
import com.PPOOII.Proyecto.Config.Model.JwtResponse;
import com.PPOOII.Proyecto.Entities.Usuario;
import com.PPOOII.Proyecto.Repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Tag(name = "Autenticación JWT", description = "Operaciones para autenticarse y obtener un token JWT")
public class JwtAuthenticationController {

    @Autowired
    private JWTAuthtenticationConfig jwtAuthtenticationConfig;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/authenticate")
    @Operation(
        summary = "Obtener JWT Token",
        description = "Autentica a un usuario con login, password y API Key. Devuelve un token JWT."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Token generado exitosamente",
            content = @Content(schema = @Schema(implementation = JwtResponse.class))
        ),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<?> createAuthenticationToken(
        // ---------- ANOTACIONES PARA EL CUERPO JSON ----------
        @RequestBody(
            description = "Objeto JSON con username y password",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = JwtRequest.class)
            )
        )
        @org.springframework.web.bind.annotation.RequestBody JwtRequest authenticationRequest,

        // ---------- ANOTACIÓN PARA EL ENCABEZADO APIkey ----------
        @Parameter(
            name = "APIKey",
            description = "Clave API asociada al usuario",
            required = true,
            in = ParameterIn.HEADER,
            example = "97282b05-7233-49b0-9dd6-875ecd6f62a1"
        )
        @RequestHeader("APIKey") String apiKey

    ) throws Exception {

        System.out.println("********************************************************************");
        System.out.println("Username: [" + authenticationRequest.getUsername() + "]");
        System.out.println("Password: [" + authenticationRequest.getPassword() + "]");
        System.out.println("APIKey:   [" + apiKey + "]");
        System.out.println("********************************************************************");

        // Buscar el usuario por login y APIKey
        Usuario usuario = usuarioRepository
            .findByLoginAndApikey(authenticationRequest.getUsername(), apiKey)
            .orElseThrow(() -> new Exception("Credenciales inválidas"));

        // Validar contraseña (sin encriptar en este caso)
        if (!usuario.getPassword().equals(authenticationRequest.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Cargar detalles del usuario en el contexto de Spring Security
        UserDetails userDetails = jwtInMemoryUserDetailsService
            .loadUserByUsername(usuario.getLogin());

        System.out.println("Autenticación exitosa para: " + userDetails.getUsername());

        // Generar token JWT
        String token = jwtAuthtenticationConfig.getJWTToken(usuario.getLogin());

        System.out.println("********************************************************************");
        System.out.println("Token generado: [" + token + "]");
        System.out.println("********************************************************************");

        return ResponseEntity.ok(new JwtResponse(token));
    }
}




