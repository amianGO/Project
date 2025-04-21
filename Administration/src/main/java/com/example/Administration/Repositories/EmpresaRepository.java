package com.example.Administration.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Administration.Entities.Empresa;


import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{
    Optional<Empresa> findByEmail(String email);
    
}
