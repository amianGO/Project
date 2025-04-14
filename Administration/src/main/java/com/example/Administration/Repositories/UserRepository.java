package com.example.Administration.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Administration.Entities.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    
}
