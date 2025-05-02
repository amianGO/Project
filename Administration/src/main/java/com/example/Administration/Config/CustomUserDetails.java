package com.example.Administration.Config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Administration.Entities.Empresa;



import lombok.Data;


@Data
public class CustomUserDetails implements UserDetails{
    private final Empresa empresa;

    public CustomUserDetails(Empresa empresa){
        this.empresa = empresa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(empresa.getRol().name()));
    }

    @Override public String getPassword() { return empresa.getPassword(); }
    @Override public String getUsername() { return empresa.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}
