package com.tfg.auth;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void loadUserByUsername() {

        Usuario usuario = new Usuario();
        usuario.setEmail("raul@gmail.com");

        when(usuarioRepository.findByEmail("raul@gmail.com"))
                .thenReturn(Optional.of(usuario));
        UserDetails result = service.loadUserByUsername("raul@gmail.com");
        assertNotNull(result);
        assertEquals("raul@gmail.com", result.getUsername());
    }

    @Test
    void loadUserByUsernameNotFound() {

        when(usuarioRepository.findByEmail("raul@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.loadUserByUsername("raul@gmail.com");
        });
    }

}
