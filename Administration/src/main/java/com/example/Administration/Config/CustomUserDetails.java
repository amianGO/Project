package com.example.Administration.Config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Administration.Entities.Empresa;



import lombok.Data;


@Data
public class CustomUserDetails implements UserDetails{                         //Esta clase es la encargada de implementar la interfaz UserDetails, que se encarga de manejar los datos del usuarios
    private final Empresa empresa;

    public CustomUserDetails(Empresa empresa){                                 //Constructor de la clase, que recibe como argumento un objeto de tipo Empresa
        this.empresa = empresa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){            //Esta funcion es la encargada de obtener los roles de las Empresas, en este caso solo uno
        return List.of(new SimpleGrantedAuthority(empresa.getRol().name()));
    }

    @Override public String getPassword() { return empresa.getPassword(); }
    @Override public String getUsername() { return empresa.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }             //Se encarga de verificar si la cuenta no ha expirado
    @Override public boolean isAccountNonLocked() { return true; }              //Se encarga de verificar si la ciuenta no esta bloqueada
    @Override public boolean isCredentialsNonExpired() { return true; }         //Se encarga de verificar si las Credenciales no ha Expirado
    @Override public boolean isEnabled() { return true; }                       //Se encarga de verificar que la cuenta este activa

}
