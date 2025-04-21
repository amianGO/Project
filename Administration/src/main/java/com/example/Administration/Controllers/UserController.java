package com.example.Administration.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Administration.Entities.Usuario;
import com.example.Administration.Services.EmpresaService;
import com.example.Administration.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService EmpresaService;

    @GetMapping("/list")
    public ResponseEntity<List<Usuario>> getAllUsers(){
        List<Usuario> usuarios = userService.getAllUsers();
        return ResponseEntity.ok(usuarios);
    }
}
