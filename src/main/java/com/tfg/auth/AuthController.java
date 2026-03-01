package com.tfg.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tfg.dto.RegisterDTO;
import com.tfg.entity.Usuario;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    

    @PostMapping("registrar")
    public ResponseEntity<Usuario> register(@RequestBody RegisterDTO request) {
        Usuario usuario=authService.register(request.nombre, request.apellido, request.email, request.password);
        
        return ResponseEntity.ok(usuario);
    }
    
    
}
