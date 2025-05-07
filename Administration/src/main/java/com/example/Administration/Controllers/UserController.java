package com.example.Administration.Controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Administration.DTO.UserDTO;


import com.example.Administration.Services.UserService;


@RestController                                     //Controlador REST para manejar las peticiones de los usuarios
@RequestMapping("/api/empresa") 
@CrossOrigin(origins = "http://localhost:3000")     //Permitir el acceso desde el frontend desde el localhost 3000
public class UserController {
    

    @Autowired
    private UserService userService;

    @GetMapping("/list/{id}/users")
    public ResponseEntity<List<UserDTO>> getUsuarioPorEmpresa(@PathVariable Long id, @RequestHeader("Authorization") String token){ //Este metodo se encarga de obtener los usuarios por empressa, recibiendo como parametro, el id de la empresa y el token
        try {
            System.out.println("Token Recibido: " + token);
            List<UserDTO> empleados = userService.getUsersByEmpresa(id);    //Busca los empleados en la base de datos
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    } 

}
