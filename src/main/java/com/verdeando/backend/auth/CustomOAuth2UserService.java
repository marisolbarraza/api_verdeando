package com.verdeando.backend.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.method.P;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.verdeando.backend.model.ProveedorAuth;
import com.verdeando.backend.model.Usuario;
import com.verdeando.backend.repository.IUsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final IUsuarioRepository usuarioRepository;
     // Use Spring's DefaultOAuth2UserService to load the user
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User usuariOAuth2 = delegate.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String email = usuariOAuth2.getAttribute("email");
        String idProveedor;
        if(provider.equals("GOOGLE")) {
            idProveedor = usuariOAuth2.getAttribute("sub");
        } else if(provider.equals("FACEBOOK")) {
            idProveedor = usuariOAuth2.getAttribute("id");
        } else {
            throw new OAuth2AuthenticationException("Proveedor no soportado");
        }

        ProveedorAuth proveedor = ProveedorAuth.valueOf(provider);
        return procesarUsuarioOAuth2(email,idProveedor,proveedor, usuariOAuth2);
            }
        
        private OAuth2User procesarUsuarioOAuth2(String email, String idProveedor, ProveedorAuth proveedor, OAuth2User usuariOAuth2) {
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
            if (usuario == null) {
            // Crear nuevo usuario si no existe
            usuario = Usuario.builder()
                    .nombre(usuariOAuth2.getAttribute("given_name"))
                    .apellido(usuariOAuth2.getAttribute("family_name"))
                    .email(email)
                    .fechaNacimiento(LocalDate.now()) // No siempre se obtiene la fecha, puedes modificar esto
                    .fechaAlta(LocalDateTime.now())
                    .isHabilitado(true)
                    .proveedoresAuth(new HashMap<>(Map.of(proveedor, idProveedor)))
                    .build();
            } else {
                usuario.getProveedoresAuth().put(proveedor, idProveedor);
            }
            usuarioRepository.save(usuario);
            return new DefaultOAuth2User(usuariOAuth2.getAuthorities(), usuariOAuth2.getAttributes(), "email");
        }
    
    
}
