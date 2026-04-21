package com.tfg.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tfg.entity.Cita;
import com.tfg.entity.Servicio;
import com.tfg.entity.Usuario;
import com.tfg.repository.CitaRepository;
import com.tfg.service.CitaService;
import com.tfg.service.ServicioService;
import com.tfg.service.UsuarioService;

@Service("CitaServiceImpl")
public class CitaServiceImpl implements CitaService{
    
    @Autowired
    @Qualifier("CitaRepository")
    private CitaRepository citaRepository;

    @Autowired
    @Qualifier("UsuarioServiceImpl")
    private UsuarioService usuarioService;

    @Autowired
    @Qualifier("ServicioServiceImpl")
    private ServicioService servicioService;
    @Override
    public Cita crearCita(Cita cita) {
        Usuario cliente = usuarioService.buscarPorId(cita.getCliente().getId());
        Servicio servicio = servicioService.buscarPorId(cita.getServicio().getId());

        cita.setCliente(cliente);
        cita.setServicio(servicio);

        cita.setHoraFinal(cita.getHoraInicio().plusHours(1));
        boolean ocupada=citaRepository.existsByFechaAndHoraInicio(cita.getFecha(), cita.getHoraInicio());
        if(ocupada){
            throw new RuntimeException("Ya existe una cita a esa hora para este dia");
        }
        return citaRepository.save(cita);
    }
    @Override
    public boolean borrarCita(int id) {
        if(citaRepository.existsById(id)){
            citaRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Override
    public Cita actualizarCita(Cita cita) { return citaRepository.save(cita);}
    @Override
    public Cita buscarPorId(int id){ return citaRepository.findById(id).orElse(null);}
    @Override
    public List<Cita> listarPorCliente(Usuario cliente) {return citaRepository.findByCliente(cliente);}
    @Override
    public List<Cita> listarPorServicio(Servicio servicio) {return citaRepository.findByServicio(servicio);}
    @Override
    public List<Cita> listarPorFecha(LocalDate fecha) {return citaRepository.findByFecha(fecha);}
    @Override
    public List<Cita> listarPorClienteAndFecha(Usuario cliente, LocalDate fecha){return citaRepository.findByClienteAndFecha(cliente, fecha);}

    @Override
    public List<LocalTime> obtenerHorasDisponibles(LocalDate fecha) {
        List<LocalTime> horasDisponibles=new ArrayList<>();
        if(fecha.isBefore(LocalDate.now())){
            return horasDisponibles;
        }
        citaRepository.findByFecha(fecha);
        LocalTime inicioJornada=LocalTime.of(9,0);
        LocalTime finJornada=LocalTime.of(18,0);
        LocalTime horaActual=inicioJornada;
        while (horaActual.isBefore(finJornada)) {

            if(fecha.equals(LocalDate.now()) && horaActual.isBefore(LocalTime.now())){
                horaActual=horaActual.plusHours(1);
                continue;
            }
            boolean ocupada=citaRepository.existsByFechaAndHoraInicio(fecha, horaActual);
            if(!ocupada){
                horasDisponibles.add(horaActual);
            }
            horaActual=horaActual.plusHours(1);
        }
        return horasDisponibles;
    }
    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }
    @Override
    public boolean existeCita(LocalDate fecha, LocalTime horaInicio) {
        return citaRepository.existsByFechaAndHoraInicio(fecha, horaInicio);
    } 
}
