package com.example.Administration.DTO;

import com.example.Administration.Entities.Empresa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String message;
    private Empresa empresa;
}
