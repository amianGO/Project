package com.example.Administration.Services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

//Generar y validad el Token
@Service
public class JwtService {
    private final String SECRET_KEY = "fyXe1OhDNClvt33MPSiuXr1Lzkuu6FUokenZEjQyYhGuhyaoGVUKnw67R4jNRSgyzNdVaI0BpN-d7KKZCAOBtQ";

    private Key getSignKey(){
        byte[] keyBytes = Base64.getUrlDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Generar el Token
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .claim("role", userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"))
            .setIssuedAt(new Date())
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
