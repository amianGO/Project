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
public class JwtService { //Esta clase es la encargada de generar la logica para el JWT

    private final String SECRET_KEY = "fyXe1OhDNClvt33MPSiuXr1Lzkuu6FUokenZEjQyYhGuhyaoGVUKnw67R4jNRSgyzNdVaI0BpN-d7KKZCAOBtQ"; //Esta es la clave secreta que se utiliza para firmar el token de 32 bits en base64

    private Key getSignKey(){ //esta funcion es la encargada de decodificar la clave secreta y convertirla en un objeto Key
        byte[] keyBytes = Base64.getUrlDecoder().decode(SECRET_KEY); //decodificamos la clave sercreta
        return Keys.hmacShaKeyFor(keyBytes); 
    }

    //Generar el Token
    public String generateToken(UserDetails userDetails){               //Esta funcion es la encargada de generar el token, recibiendo los datos de UserDetails
        return Jwts.builder()                                           //Esta funcion es la encargada de construir el token
            .setSubject(userDetails.getUsername())                      //setSubject asigna el eamil al token
            .claim("role", userDetails.getAuthorities().stream()   // esta funcion se encarga de obtener el rol de la empresa
                .findFirst()                                            // Busca el primer rol del usuario
                .map(GrantedAuthority::getAuthority)                    //Obtiene el rol de la emoresa
                .orElse("ROLE_USER"))                             //Asigna el rol en caso de que no lo tenga
            .setIssuedAt(new Date())                                    //Asigna la fecha de emision del token
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) //Asigna la fecha de expiracion del token, en este caso 24 horas
            .signWith(getSignKey(), SignatureAlgorithm.HS512)           //Asigna la firma del token, en este caso bajo HS512
            .compact();                                                 //Esta funcion es la encargada de compactar el token, y devolverlo como un String
    }

    //Obtener el email desde el Token
    public String extractEmail(String token){       //Esta es la funcion encargada de extraer el email del token, recibiendo el token como parametro
        return Jwts.parserBuilder()                 //Essta funcion es la encargada de parsear el token para obtener los datos
            .setSigningKey(getSignKey())            //Esta funcion es la encargada de asignarle la clave secreta al token
            .build()                                //Esta es la funcion encargada de construir el token
            .parseClaimsJws(token)                  //Esta funcion es la encargada de parsear el token 
            .getBody()                              //Esta funcion es la encargada de obtener el cuerpo del token 
            .getSubject();                          //Esta funcion es la encargada de obtener el email del token
    }

    //Validamos el Token
    public boolean isTokenValid(String token, String email){ //Esta funcion es la encargada de validar el token, recibiendo el token y el email
        final String extractedEmail = extractEmail(token); //Buscamos el eamil asignado al token
        return (extractedEmail.equals(email) &&  !isTokenExpired(token)); //Validamos que el eamil extraido sea igual al del parametro y que el tokenn no haya expirado
    }

    public boolean isTokenExpired(String token){    //Esta funcion es la encargada de verificar la expiracion del token que recibe
        Date expiration = Jwts.parserBuilder()      //Parsea el token guardando la fecha de expiracion
            .setSigningKey(getSignKey())            //Asigna la clave secreta 
            .build()                                //Construye el token
            .parseClaimsJws(token)                  //Parseamos el token
            .getBody()                              //Obtiene el cuerpo del token
            .getExpiration();                       //Obtiene la fecha de expiracion del token
        
        return expiration.before(new Date());       //verificamos si la fecha del token, ha expirado si es menor que la fecha del momento, en ese caso retorna un true o expirado
    }
}
