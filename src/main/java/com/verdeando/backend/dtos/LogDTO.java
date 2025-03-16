package com.verdeando.backend.dtos;

import com.verdeando.backend.model.TipoAccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogDTO {
    private String idLog;
    private  TipoAccion tipoAccion;
    private String fechaAccion;
    private UsuarioDTO usuario;
    
    private String detalle;
}
