package com.example.Administration.Controllers;



import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Administration.Components.EmpresaMapper;
import com.example.Administration.DTO.LoginDTO;
import com.example.Administration.DTO.RegisterDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Services.EmpresaService;
import com.example.Administration.Services.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {                                                                       //Es el Controlador de la API de Autenticacion
    
    @Autowired
    private EmpresaService EmpresaService;                                                          //Es el Servicio de Usuario que se encarga de la logica del negocio

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmpresaMapper empresaMapper;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){                        //Es un Metodo para Registrar un nuevo usuario, que utillizar ResponseEntity para devolver una Respuesta HTTP
        try {
            EmpresaService.register(registerDTO);
            return ResponseEntity.ok("Usuario Registrado con exito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginDTO loginDTO){                                 //Es un Metodo para Login, que utiliza ResponseEntity para devolver una respuesta HTTP
        try {
            Empresa empresa = EmpresaService.login(loginDTO);                                       //Se encarga de realizar el login y devulve la Empresa
            
            UserDetails userDetails = empresaMapper.toUserDetails(empresa);                         //Convierte la empresa en UserDetails
            String token = jwtService.generateToken(userDetails);                                   //Genera el token a partir de los detalles del usuario
            
            Map<String, Object> response = new HashMap<>();                                         //Creamos un mapa para almacenar la respuesta
            response.put("token", token);
            response.put("empresa", empresa);
        
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
   

}
