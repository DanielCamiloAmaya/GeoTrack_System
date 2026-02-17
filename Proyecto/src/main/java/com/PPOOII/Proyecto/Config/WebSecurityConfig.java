package com.PPOOII.Proyecto.Config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.PPOOII.Proyecto.Repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public WebSecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JWTAuthorizationFilter jwtAuthorizationFilter =
            new JWTAuthorizationFilter(usuarioRepository);

        http
            // 1) Habilita CORS usando la configuración de WebMvcConfig.addCorsMappings(...)
            .cors(withDefaults())

            // 2) Desactiva CSRF (porque usas JWT stateless)
            .csrf(csrf -> csrf.disable())

            // 3) Políticas de autorización
            .authorizeHttpRequests(authz -> authz
                // Permite todos los preflight CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // La ruta de autenticación abierta
                .requestMatchers("/authenticate").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // El resto requiere token válido
                .anyRequest().authenticated()
            )

            // 4) Stateless sessions
            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 5) Añade tu filtro de JWT antes del filtro de usuario/contraseña tradicional
            .addFilterBefore(jwtAuthorizationFilter,
                            UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig
    ) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}


