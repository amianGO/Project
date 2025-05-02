package com.example.Administration.DTO;

import java.io.Serializable;

import com.example.Administration.Entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable{
    private Long id;
    private String name;
    private String email;
    private String password;
    private String rolName;

    public UserDTO(Usuario usuario){
        this.id = usuario.getId();
        this.name = usuario.getName();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.rolName = usuario.getRol().name();
    }
}
