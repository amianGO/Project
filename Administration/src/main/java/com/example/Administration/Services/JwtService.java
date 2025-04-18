package com.example.Administration.Services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//Generar y validad el Token
@Service
public class JwtService {
    private final String SECRET_KEY = "KGAWO7Xt9uLvESscuhll686ptFYu+/Y+hiFLX4gu20LnsXwLayoRysmpCtd/HR4glWxZGaNeaKqnXy3xMQdclA==";

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    //Generar el Token
    public String generateToken(String email){
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getSignKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    //Obtener el email desde el Token
    public String extractEmail(String token){
        return Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    //Validamos el Token
    public boolean isTokenValid(String token, String email){
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) &&  !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration();
        
        return expiration.before(new Date());
    }
}
