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
		SpringApplication.run(AdministrationApplication.class, args);
		System.out.println("Hola Mundo");

		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		System.out.println(Encoders.BASE64.encode(key.getEncoded()));
	}

}
