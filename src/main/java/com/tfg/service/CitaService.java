package com.tfg.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.tfg.entity.Cita;
import com.tfg.entity.Usuario;
import com.tfg.entity.Servicio;


public interface CitaService {
    public abstract List<Cita> listarCitas();
    public abstract boolean existeCita(LocalDate fecha, LocalTime horaInicio);
    public abstract Cita crearCita(Cita cita);
    public abstract boolean borrarCita(int id);
    public abstract Cita actualizarCita(Cita cita);
    public abstract List<Cita> listarPorCliente(Usuario cliente);
    public abstract List<Cita> listarPorServicio(Servicio servicio);
    public abstract List<Cita> listarPorFecha(LocalDate fecha);
    public abstract List<Cita> listarPorClienteAndFecha(Usuario cliente, LocalDate fecha);
    public abstract List<LocalTime> obtenerHorasDisponibles(LocalDate fecha);
}