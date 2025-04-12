package com.example.Administration.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO { //Es un Resumen de los datos que se necesitan para iniciar sesion
    private String email;
    private String password;
}
