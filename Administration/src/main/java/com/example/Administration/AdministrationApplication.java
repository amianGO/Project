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
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        System.out.println("DB_URL: " + dbUrl);
        System.out.println("DB_USER: " + dbUser);
        System.out.println("DB_PASS: " + dbPass);

        SpringApplication.run(AdministrationApplication.class, args);
	}

}
