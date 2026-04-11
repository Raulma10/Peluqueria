package com.tfg.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void registrado() {
        when(passwordEncoder.encode("1234")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario usuario = authService.register(
                "Raul",
                "Martin",
                "raul@gmail.com",
                "1234" 
        );

        assertNotNull(usuario);
        assertEquals("Raul", usuario.getNombre());
        assertEquals("encodedPassword", usuario.getPassword());
        assertEquals(Rol.CLIENTE, usuario.getRol());
    }

    @Test
    void registerEmailDuplicado() {

        when(usuarioRepository.existsByEmail("raul@gmail.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> {
            authService.register("Raul","Martin","raul@gmail.com","1234");
        });
    }
}
