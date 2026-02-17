package com.PPOOII.Proyecto.Config.Model;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class Constans {
    public static final String LOGIN_URL = "/authenticate";
    public static final String HEADER_APIKEY = "APIKey";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    
    // Clave secreta en Base64 segura
    public static final String SUPER_SECRET_KEY = "ZnJhc2VzbGFyZ2FzcGFyYWNvbG9jYXJjb21vY2xhdmVlbnVucHJvamVjdG9kZWVtZXBsb3BhcmFqd3Rjb25zcHJpbmdzZWN1cml0eQ==bWlwcnVlYmFkZWVqbXBsb3BhcmFiYXNlNjQ=";
    
    public static final long TOKEN_EXPIRATION_TIME = 864000000L;
    
    // Método sin parámetro que usa la clave definida en SUPER_SECRET_KEY
    public static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SUPER_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // Constructor vacío (opcional)
    public Constans() {}
}
