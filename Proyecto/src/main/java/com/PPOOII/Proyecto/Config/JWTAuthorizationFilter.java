package com.PPOOII.Proyecto.Config;

import com.PPOOII.Proyecto.Config.Model.Constans;
import com.PPOOII.Proyecto.Repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;              
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;

    public JWTAuthorizationFilter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private Claims getClaims(HttpServletRequest request) {
        String token = request.getHeader(Constans.HEADER_AUTHORIZACION_KEY)
                .replace(Constans.TOKEN_BEARER_PREFIX, "");
        return Jwts.parserBuilder()
                .setSigningKey(Constans.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private void setAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities != null
                    ? authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    : null
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean isJWTValid(HttpServletRequest request) {
        String header = request.getHeader(Constans.HEADER_AUTHORIZACION_KEY);
        return (header != null && header.startsWith(Constans.TOKEN_BEARER_PREFIX));
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        try {
            String apiKey = request.getHeader(Constans.HEADER_APIKEY);
            if (isJWTValid(request) && apiKey != null) {
                Claims claims = getClaims(request);
                String username = claims.getSubject();
                if (usuarioRepository.findByLoginAndApikey(username, apiKey).isPresent()) {
                    setAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}



