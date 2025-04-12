package com.example.Administration.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Administration.Entities.Empresa;
import com.example.Administration.Repositories.EmpresaRepository;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Empresa register(Empresa user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encriptamos la contraseña con BCrypt
        return repo.save(user);
    }

    public Optional<Empresa> login(String email, String password){ 
        return repo.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword())); //Comparamos la contraseña Encriptada con la que nos llega por el Login
    }

    public Optional<Empresa> findEmail(String email){
        return repo.findByEmail(email);
    }
}
