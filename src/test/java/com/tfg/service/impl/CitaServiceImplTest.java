package com.tfg.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tfg.entity.Cita;
import com.tfg.entity.Servicio;
import com.tfg.entity.Usuario;
import com.tfg.repository.CitaRepository;
import com.tfg.service.ServicioService;
import com.tfg.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class CitaServiceImplTest {

    @InjectMocks
    private CitaServiceImpl citaService;

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private ServicioService servicioService;

    private Usuario usuario;
    private Servicio servicio;
    private Cita cita;
    
    @Test
    void crearCita() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));
        when(usuarioService.buscarPorId(1)).thenReturn(usuario);
        when(servicioService.buscarPorId(1)).thenReturn(servicio);
        when(citaRepository.existsByFechaAndHoraInicio(any(), any())).thenReturn(false);
        when(citaRepository.save(any())).thenReturn(cita);

        Cita resultado = citaService.crearCita(cita);

        assertNotNull(resultado);
        assertEquals(LocalTime.of(11,0), resultado.getHoraFinal());
    }

    @Test
    void crearCitaHoraOcupada() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));
        when(usuarioService.buscarPorId(1)).thenReturn(usuario);
        when(servicioService.buscarPorId(1)).thenReturn(servicio);
        when(citaRepository.existsByFechaAndHoraInicio(any(), any())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            citaService.crearCita(cita);
        });

        assertEquals("Ya existe una cita a esa hora para este dia", ex.getMessage());
    }

    @Test
    void borrarCita() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));
        when(citaRepository.existsById(1)).thenReturn(true);

        boolean resultado = citaService.borrarCita(1);

        assertTrue(resultado);
        verify(citaRepository).deleteById(1);
    }

    @Test
    void borrarCitaNoExiste() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));
        when(citaRepository.existsById(1)).thenReturn(false);

        boolean resultado = citaService.borrarCita(1);

        assertFalse(resultado);
    }

    @Test
    void obtenerHorasDisponibles() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));

        LocalDate fecha = LocalDate.now().plusDays(1);

        when(citaRepository.existsByFechaAndHoraInicio(any(), any()))
                .thenReturn(false);

        List<LocalTime> horas = citaService.obtenerHorasDisponibles(fecha);

        assertFalse(horas.isEmpty());
        assertTrue(horas.contains(LocalTime.of(9,0)));
    }

    @Test
    void obtenerHorasDisponiblesFechaPasada() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));

        LocalDate fecha = LocalDate.now().minusDays(1);

        List<LocalTime> horas = citaService.obtenerHorasDisponibles(fecha);

        assertTrue(horas.isEmpty());
    }

    @Test
    void obtenerHorasDisponiblesFiltraOcupadas() {
        usuario=new Usuario();
        usuario.setId(1);
        servicio=new Servicio();
        servicio.setId(1);
        cita =new Cita();
        cita.setCliente(usuario);
        cita.setServicio(servicio);
        cita.setFecha(LocalDate.now().plusDays(1));
        cita.setHoraInicio(LocalTime.of(10,0));
        
        LocalDate fecha = LocalDate.now().plusDays(1);

        when(citaRepository.existsByFechaAndHoraInicio(eq(fecha), any()))
            .thenAnswer(invocation -> {
                LocalTime hora = invocation.getArgument(1);
                return hora.equals(LocalTime.of(10,0));
            });

        List<LocalTime> horas = citaService.obtenerHorasDisponibles(fecha);

        assertFalse(horas.contains(LocalTime.of(10,0)));
    }
}
