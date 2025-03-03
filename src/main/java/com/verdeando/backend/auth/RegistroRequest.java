package com.verdeando.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
    String nombre;
    String apellido;
    String email;
    String contrasenia;
    LocalDate fechaNacimiento;
}
