package com.PPOOII.Proyecto.Config;

import com.PPOOII.Proyecto.Config.Model.Constans;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

@Configuration
public class JWTAuthtenticationConfig {

    public JWTAuthtenticationConfig() {
    }

    public String getJWTToken(String username) {
        // Define autoridades; en este ejemplo se asigna ROLE_USER.
        List<GrantedAuthority> grantedAuthorities = 
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        
        // Construimos el token utilizando la llave de Constans.
        String token = Jwts.builder()
                .setId("PPOOII_JWT")
                .setSubject(username)
                .claim("authorities", 
                    grantedAuthorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constans.TOKEN_EXPIRATION_TIME))
                .signWith(Constans.getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
        
        return Constans.TOKEN_BEARER_PREFIX + token;
    }
}
