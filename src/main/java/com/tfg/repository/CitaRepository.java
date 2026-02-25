package com.tfg.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.entity.Cita;
import com.tfg.entity.Servicio;
import com.tfg.entity.Usuario;

@Repository("CitaRepository")
public interface CitaRepository extends JpaRepository<Cita,Integer>{
    boolean existsByFechaAndHoraInicio(LocalDate fecha, LocalTime horaInicio);
    List<Cita>findByFecha(LocalDate fecha);
    List<Cita>findByServicio(Servicio servicio);
    List<Cita>findByCliente(Usuario cliente);
    List<Cita>findByClienteAndFecha(Usuario cliente, LocalDate fecha);
}
