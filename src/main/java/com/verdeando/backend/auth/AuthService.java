package com.verdeando.backend.auth;

import com.verdeando.backend.jwt.JwtService;
import com.verdeando.backend.model.TipoAccion;
import com.verdeando.backend.model.Usuario;
import com.verdeando.backend.repository.IUsuarioRepository;
import com.verdeando.backend.service.LogService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LogService logService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getContrasenia()));
        UserDetails usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);
        logService.guardarLog((Usuario) usuario, TipoAccion.LOGIN, "Usuario logueado: " + usuario.getUsername());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse registro(RegistroRequest request) {
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contrasenia(passwordEncoder.encode(request.getContrasenia()))
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaAlta(LocalDateTime.now())
                .isHabilitado(true)
                .build();
        usuarioRepository.save(usuario);
        String detalle = "Usuario registrado: " + usuario.getEmail() +  "," + usuario.getNombre() + ", " + 
                        usuario.getApellido()+ ", Fecha Nacimiento: " + usuario.getFechaNacimiento()+ ", Fecha Alta: " + usuario.getFechaAlta()+ 
                        ", Está habilitado?: " + usuario.getIsHabilitado();
        logService.guardarLog(usuario, TipoAccion.REGISTRO, detalle);
        
        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

}
