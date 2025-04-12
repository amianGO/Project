package com.example.Administration.Controllers;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Administration.DTO.LoginDTO;
import com.example.Administration.DTO.RegisterDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Services.EmpresaService;

@RestController
@RequestMapping("/api/auth")
public class AuthController { //Es el Controlador de la API de Autenticacion
    
    @Autowired
    private EmpresaService userService; //Es el Servicio de Usuario que se encarga de la logica del negocio

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){ //Es un Metodo para Registrar un nuevo usuario, que utillizar ResponseEntity para devolver una Respuesta HTTP
        Empresa NewEmpresa = new Empresa();
        NewEmpresa.setEmail(registerDTO.getEmail());
        NewEmpresa.setName(registerDTO.getName());
        NewEmpresa.setPassword(registerDTO.getPassword());

        userService.register(NewEmpresa);
        return ResponseEntity.ok("Usuario Registrado con exito");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){ //Es un Metodo para Login, que utiliza ResponseEntity para devolver una respuesta HTTP

        //Este es un Objeto que contiene los datos del Login
        Optional<Empresa> usuario = userService.login(loginDTO.getEmail(), loginDTO.getPassword());

        if (usuario.isPresent()) {
            return ResponseEntity.ok("Login Exitoso");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Incorrectas");
        }
    }
   

}
