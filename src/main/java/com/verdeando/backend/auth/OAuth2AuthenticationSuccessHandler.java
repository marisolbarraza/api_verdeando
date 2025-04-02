package com.verdeando.backend.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.verdeando.backend.jwt.JwtService;
import com.verdeando.backend.model.TipoAccion;
import com.verdeando.backend.model.Usuario;
import com.verdeando.backend.repository.IUsuarioRepository;
import com.verdeando.backend.service.LogService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final IUsuarioRepository usuarioRepository;
    private final LogService logService;
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, java.io.IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.getToken(usuario);
        String detalle = "Usuario registrado: " + usuario.getEmail() +  "," + usuario.getNombre() + ", " + 
                        usuario.getApellido()+ ", Fecha Nacimiento: " + usuario.getFechaNacimiento()+ ", Fecha Alta: " + usuario.getFechaAlta()+ 
                        ", Está habilitado?: " + usuario.getIsHabilitado();
        logService.guardarLog(usuario, TipoAccion.LOGIN, detalle);
        AuthResponse authResponse = AuthResponse.builder().token(token).build();
        // Configurar respuesta HTTP
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(authResponse));
    }

}
