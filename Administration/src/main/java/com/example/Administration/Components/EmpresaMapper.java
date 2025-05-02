package com.example.Administration.Components;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.Administration.Config.CustomUserDetails;
import com.example.Administration.Entities.Empresa;

@Component
public class EmpresaMapper {
    public UserDetails toUserDetails(Empresa empresa){
        return new CustomUserDetails(empresa);
    }
}
