package com.tfg.auth;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public Usuario login(String email, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        
        List<Usuario>usuarios=usuarioRepository.findByEmail(email);

        if(usuarios.isEmpty()){
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario=usuarios.get(0);
        if(!passwordEncoder.matches(password, usuario.getPassword())){
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    public Usuario register(String nombre, String apellido, String email, String password){
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
