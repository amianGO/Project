package com.example.Administration.Services;


import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Administration.DTO.LoginDTO;
import com.example.Administration.DTO.RegisterDTO;
import com.example.Administration.DTO.UserDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Entities.Rol;

import com.example.Administration.Repositories.EmpresaRepository;

@Service
public class EmpresaService {                               //Esta es la clase encargada de gestionar la logica para las empresas
    
    @Autowired
    private EmpresaRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public Empresa register(RegisterDTO registerDTO){       //Este metodo es el encargado de registrar la empresa, recibiendo como argumento un DTO

        Optional<Empresa> optionalEmpresa = repo.findByEmail(registerDTO.getEmail()); //Busca la empresa por email en la base de datos
        if (optionalEmpresa.isPresent()) {
            throw new RuntimeException("La Empresa ya esta Registrada");
        }
        Empresa empresa = new Empresa();                    //Creamos una nueva empresa

        empresa.setEmail(registerDTO.getEmail());
        empresa.setName(registerDTO.getName());
        empresa.setRol(Rol.EMPRESA);
        empresa.setPassword(passwordEncoder.encode(registerDTO.getPassword())); //Encriptamos la contraseña con BCrypt

        Empresa savedEmpresa = repo.save(empresa);          //Guardamos la empresa en la base de datos

        //Crear el Primer Usuario asociado a la Empresa
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(registerDTO.getEmail());
        userDTO.setName(registerDTO.getName());
        userDTO.setPassword(registerDTO.getPassword());     //Sin encriptar, el user service se encarga de hacerlo
        

        userService.createFirstUser(userDTO, savedEmpresa); //llamamos el metodo para crear el primer usuario con sus respectivos argumentos

        return savedEmpresa;
    }

    public Empresa login(LoginDTO loginDTO){                //Este metodo se encarga de loggear las empresas recibiendo, un DTO

        Optional<Empresa> optionalEmpresa = repo.findByEmail(loginDTO.getEmail()); //Buscamos la empresa en la base de datos

        if (optionalEmpresa.isEmpty()) {
            throw new RuntimeException("La Empresa no esta Registrada");
        }

        Empresa empresa = optionalEmpresa.get();            //Obtenemos la empresa de la base de datos

        if (!passwordEncoder.matches(loginDTO.getPassword(), empresa.getPassword())) { //Si la contraseña no coincide con la de la base de datos, retornamos un error
            throw new RuntimeException("Credenciales Incorrectas");
        }

        return empresa;
    }

    
}
