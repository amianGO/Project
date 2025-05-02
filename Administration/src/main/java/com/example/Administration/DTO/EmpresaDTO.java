package com.example.Administration.DTO;

import java.util.List;

import com.example.Administration.Entities.Empresa;
import com.example.Administration.Entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    private Long id;
    private String name;
    private String email;
    private String rol;
    private List<Usuario> empleados;

    public EmpresaDTO(Empresa empresa){
        this.id = empresa.getId();
        this.name = empresa.getName();
        this.email = empresa.getEmail();
        this.rol = empresa.getRol().name();
        this.empleados = empresa.getEmpleados();
    }
}
