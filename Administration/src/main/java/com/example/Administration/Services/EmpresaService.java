package com.example.Administration.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Administration.DTO.LoginDTO;
import com.example.Administration.DTO.RegisterDTO;
import com.example.Administration.DTO.UserDTO;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Entities.Rol;
import com.example.Administration.Entities.Usuario;
import com.example.Administration.Repositories.EmpresaRepository;

@Service
public class EmpresaService {
    
    @Autowired
    private EmpresaRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    public Empresa register(RegisterDTO registerDTO){

        Optional<Empresa> optionalEmpresa = repo.findByEmail(registerDTO.getEmail());
        if (optionalEmpresa.isPresent()) {
            throw new RuntimeException("La Empresa ya esta Registrada");
        }
        Empresa empresa = new Empresa();

        empresa.setEmail(registerDTO.getEmail());
        empresa.setName(registerDTO.getName());
        empresa.setRol(Rol.ROLE_EMPRESA);
        empresa.setPassword(passwordEncoder.encode(registerDTO.getPassword())); //Encriptamos la contraseña con BCrypt

        Empresa savedEmpresa = repo.save(empresa);

        //Crear el Primer Usuario asociado a la Empresa
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(registerDTO.getEmail());
        userDTO.setName(registerDTO.getName());
        userDTO.setPassword(registerDTO.getPassword()); //Sin encriptar, el user service se encarga de hacerlo

        userService.createFirstUser(userDTO, savedEmpresa);

        return savedEmpresa;
    }

    public Empresa login(LoginDTO loginDTO){ 
        Optional<Empresa> optionalEmpresa = repo.findByEmail(loginDTO.getEmail());

        if (optionalEmpresa.isEmpty()) {
            throw new RuntimeException("La Empresa no esta Registrada");
        }

        Empresa empresa = optionalEmpresa.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), empresa.getPassword())) {
            throw new RuntimeException("Credenciales Incorrectas");
        }

        return empresa;
    }

    public List<Usuario> getUsersByEmpresa(Long empresaId){
        Empresa empresa = repo.findById(empresaId)
            .orElseThrow(() -> new RuntimeException("Empresa no Encontrada"));
        return empresa.getEmpleados();
    }

    public Optional<Empresa> findEmail(String email){
        return repo.findByEmail(email);
    }
}
