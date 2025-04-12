package com.example.Administration.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Administration.Entities.User;
import com.example.Administration.Repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encriptamos la contraseña con BCrypt
        return repo.save(user);
    }

    public Optional<User> login(String email, String password){ 
        return repo.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword())); //Comparamos la contraseña Encriptada con la que nos llega por el Login
    }

    public Optional<User> findEmail(String email){
        return repo.findByEmail(email);
    }
}
