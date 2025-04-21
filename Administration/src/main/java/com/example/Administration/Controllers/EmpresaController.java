package com.example.Administration.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Administration.Entities.Usuario;
import com.example.Administration.Services.EmpresaService;

@PreAuthorize("hasAuthority('ROLE_EMPRESA')")
@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/list/{id}/users")
    public ResponseEntity<List<Usuario>> getUsuarioPorEmpresa(@PathVariable Long id){
        List<Usuario> empleados = empresaService.getUsersByEmpresa(id);
        return ResponseEntity.ok(empleados);
    } 

}
