package com.example.Administration.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Administration.DTO.UserDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Entities.Rol;
import com.example.Administration.Entities.Usuario;
import com.example.Administration.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createFirstUser(UserDTO userDTO, Empresa empresa){
        Usuario usuario = new Usuario();

        usuario.setEmail(userDTO.getEmail());
        usuario.setName(userDTO.getName());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuario.setEmpresa(empresa);

        return userRepository.save(usuario);
        
    }
}
