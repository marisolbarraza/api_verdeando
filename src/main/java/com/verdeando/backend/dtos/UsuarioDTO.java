package com.verdeando.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String id;  
    private String nombre;
    private String apellido;
    private String email;

}
