package com.example.Administration.Config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.Administration.Components.JwtAuthFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig { //Es una clase de configuracion de seguridad 
    
    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){                           //CORS configuration
        CorsConfiguration configuration = new CorsConfiguration();                      //Permitir el accesi a la API desde ek frontend
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));           //URL del frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); //Metodos permitidos
        configuration.setAllowedHeaders(List.of("*"));                               //Cabeceras Permitidas
        configuration.setAllowCredentials(true);                       //Permitir Credenciales
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //Configuracion de la URL
        source.registerCorsConfiguration("/**", configuration);                 //Permitir el acceso a todas las URL
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{        //Este metodo se encarga de la configuracion de seguridad de la aplicacion
        http 
                .csrf(csrf -> csrf.disable()) //Desactiva la proteccion CSRF para permitir el acceso a la API desde el Frontend
                .cors(cors -> corsConfigurationSource()) //Activa la configuracion de CORS para permitir el acceso a la API desde el Frontend
                .authorizeHttpRequests(requests -> requests //Permite las Peticiones con http
                        .requestMatchers("/api/auth/**").permitAll() //Permite el acceso a la API de autenticacion sin necesidad de estar autenticado
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated() //Cualquier otra peticion necesita aitenticacion
                    ) 
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); //Agrega el filtro JWT

        return http.build(); //Construye el Objeto con la configuracion de seguridad
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){ //Este metodo se encarga de la configuracion del PasswordEncoder
        return new BCryptPasswordEncoder(); //Es utilizado para Encriptar la contraseña de los usuarios
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
