package com.tfg.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario register(String nombre, String apellido, String email, String password){
        if(usuarioRepository.existsByEmail(email)){
            throw new RuntimeException("El email ya está registrado");
        }
        Usuario usuario=Usuario.builder()
        .nombre(nombre)
        .apellido(apellido)
        .email(email)
        .password(passwordEncoder.encode(password))
        .rol(Rol.CLIENTE)
        .build();

       return usuarioRepository.save(usuario);
    }
}
