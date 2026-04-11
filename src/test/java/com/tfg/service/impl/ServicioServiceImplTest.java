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

import com.tfg.entity.Servicio;
import com.tfg.repository.ServicioRepository;

@ExtendWith(MockitoExtension.class)
public class ServicioServiceImplTest {

    @InjectMocks
    private ServicioServiceImpl servicioService;

    @Mock
    private ServicioRepository servicioRepository;

    @Test
    void crearServicio() {

        Servicio servicio = new Servicio();
        servicio.setNombre("Corte");
        servicio.setPrecio(10.0);

        when(servicioRepository.existsByNombre("Corte")).thenReturn(false);
        when(servicioRepository.save(any())).thenReturn(servicio);

        Servicio resultado = servicioService.crearServicio(servicio);

        assertNotNull(resultado);
        verify(servicioRepository).save(servicio);
    }

    @Test
    void crearServicioDuplicado() {

        Servicio servicio = new Servicio();
        servicio.setNombre("Corte");

        when(servicioRepository.existsByNombre("Corte")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servicioService.crearServicio(servicio);
        });
        assertEquals("El servicio con el nombre Corte ya está creado", ex.getMessage());
    }

    @Test
    void buscarPorId() {

        Servicio servicio = new Servicio();
        servicio.setId(1);

        when(servicioRepository.findById(1)).thenReturn(Optional.of(servicio));

        Servicio resultado = servicioService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorIdNoExiste() {

        when(servicioRepository.findById(1)).thenReturn(Optional.empty());

        Servicio resultado = servicioService.buscarPorId(1);
        assertNull(resultado);
    }

    @Test
    void borrarServicio() {

        when(servicioRepository.existsById(1)).thenReturn(true);

        boolean resultado = servicioService.borrarServicio(1);
        assertTrue(resultado);
        verify(servicioRepository).deleteById(1);
    }

    @Test
    void borrarServicioNoExiste() {

        when(servicioRepository.existsById(1)).thenReturn(false);

        boolean resultado = servicioService.borrarServicio(1);
        assertFalse(resultado);
    }

    @Test
    void actualizarServicio() {

        Servicio existente = new Servicio();
        existente.setId(1);
        existente.setNombre("Corte");

        Servicio actualizado = new Servicio();
        actualizado.setId(1);
        actualizado.setNombre("Barba");
        actualizado.setPrecio(15.0);

        when(servicioRepository.findById(1)).thenReturn(Optional.of(existente));
        when(servicioRepository.save(any())).thenReturn(existente);

        Servicio resultado = servicioService.actualizarServicio(actualizado);

        assertEquals("Barba", resultado.getNombre());
    }

    @Test
    void actualizarServicioNoExiste() {

        Servicio servicio = new Servicio();
        servicio.setId(1);

        when(servicioRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            servicioService.actualizarServicio(servicio);
        });
    }
}
