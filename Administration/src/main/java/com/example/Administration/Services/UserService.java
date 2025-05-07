package com.example.Administration.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Administration.DTO.UserDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Entities.Rol;
import com.example.Administration.Entities.Usuario;
import com.example.Administration.Repositories.EmpresaRepository;
import com.example.Administration.Repositories.UserRepository;

@Service
public class UserService {                                                  //Esta clase es la encargada de gestiornar la logica para los usuarios

    @Autowired
    private EmpresaRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario createFirstUser(UserDTO userDTO, Empresa empresa){       //Con este metodo crearemos el primer usuario, recibe el DTO y la empresa a la que pertenece
        Usuario usuario = new Usuario();                                    //Creamos un nuevo usuario

        usuario.setEmail(userDTO.getEmail());
        usuario.setName(userDTO.getName());
        usuario.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuario.setEmpresa(empresa);

        return userRepository.save(usuario);
        
    }

    public Usuario createUser(Usuario usuario){                                //Con este metodo crearemos un nuevo usuarios, el cual utiliza como argumento la entidad correspondiente
        Optional<Usuario> optionalUser = userRepository.findByName(usuario.getName()); //Buscamos si el usuario ya existe en la base de datos
        if (optionalUser.isPresent()) {
            throw new RuntimeException("El usuario ya esta registrado.");
        }
        usuario.setPassword( passwordEncoder.encode(usuario.getPassword()));    //Encriptamos la contraseña del usuario
        return userRepository.save(usuario);
    }

    public List<Usuario> getAllUsers(){                                         //Con este metodo obtendremos todos los usuarios creados en la base de datos
        List<Usuario> usuarios = userRepository.findAll();                      //Buscamos todos los usuarios en la base de datos
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios Registrados");
        }
        System.out.println(usuarios);
        return usuarios;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByEmpresa(Long id){                            //Con este metodo buscaremos todos los usuarios de una empresa en especifico, recibe como argumento el id de la empresa
        Empresa empresa = repo.findById(id)                                     //Buscamos la empresa en la base de datos
            .orElseThrow(() -> new RuntimeException("Empresa no Encontrada"));
            
        return empresa.getEmpleados().stream()                                  //Obtenemos todos los empleados de la empresa y lo convertimos a un DTO
            .map(usuario -> new UserDTO(usuario))                               //mapeamos el usuario al DTO
            .collect(Collectors.toList());                                      //collect funciona como un tolist, que convierte el stream en una lista
    }

    public Optional<Empresa> findEmail(String email){
        return repo.findByEmail(email);
    }


}
