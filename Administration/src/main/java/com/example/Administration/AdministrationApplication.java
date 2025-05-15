package com.example.Administration;

import java.security.Key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@SpringBootApplication
public class AdministrationApplication {

	public static void main(String[] args) {
		
		System.out.println("Hola Mundo");

		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		System.out.println("Clave segura " + Encoders.BASE64URL.encode(key.getEncoded()));

		// Usar Spring Boot para acceder a las variables de entorno
        String dbUrl = System.getenv("DATABASE_PUBLIC_URL");
        String dbUser = System.getenv("PGUSER");
        String dbPass = System.getenv("PGPASSWORD");

        System.out.println("DATABASE_PUBLIC_URL: " + dbUrl);
        System.out.println("PGUSER: " + dbUser);
        System.out.println("PGPASSWORD: " + dbPass);

        SpringApplication.run(AdministrationApplication.class, args);
	}

}
