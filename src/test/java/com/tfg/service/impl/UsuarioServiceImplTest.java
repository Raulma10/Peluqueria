package com.tfg.service.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Usuario usuario;

    @Test
    void crearUsuarioTest(){

        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(usuario.getPassword())).thenReturn("encodePassWord");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado=usuarioService.crearUsuario(usuario);
        assertNotNull(resultado);
        verify(usuarioRepository).save(usuario);
        verify(passwordEncoder).encode("1234");
    }

    @Test
    void crearUsuarioTestEmailDuplicado(){
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);
        RuntimeException ex = assertThrows(RuntimeException.class, ()->{
            usuarioService.crearUsuario(usuario);
        });
        assertEquals("El email ya está registrado", ex.getMessage());
    }

    @Test
    void buscarPorIdExiste(){
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        Usuario resultado=usuarioService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals("Raul", resultado.getNombre());
    }

    @Test
    void buscarPorIdNoExiste(){
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());
        Usuario resultado= usuarioService.buscarPorId(1);
        assertNull(resultado);
    }

    @Test
    void borrarUsuario(){
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.existsById(1)).thenReturn(true);
        boolean resultado=usuarioService.borrarUsuario(1);
        assertTrue(resultado);
        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void borrarUsuarioNoExiste(){
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.existsById(1)).thenReturn(false);
        boolean resultado=usuarioService.borrarUsuario(1);
        assertFalse(resultado);
    }

    @Test 
    void actualizarUsuario_ok() {
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        Usuario actualizado = new Usuario("Roberto", "Martínez", "rober@gmail.com", "1234", Rol.CLIENTE);
        actualizado.setId(1);

        Usuario resultado = usuarioService.actualizarUsuario(actualizado);

        assertEquals("Roberto", resultado.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void actualizarUsuarioNoExiste() {
        usuario= new Usuario("Raul","Martin","raul@gmail.com","1234",Rol.CLIENTE);
        usuario.setId(1);
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        Usuario actualizado = new Usuario();
        actualizado.setId(1);

        assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarUsuario(actualizado);
        });
    }
}
