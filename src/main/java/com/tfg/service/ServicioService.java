package com.tfg.service;

import java.util.List;

import com.tfg.entity.Servicio;

public interface ServicioService {
	public abstract List<Servicio> listarServicios();
	public abstract Servicio crearServicio(Servicio servicio);
	public abstract boolean borrarServicio(int id);
	public abstract Servicio actualizarServicio(Servicio servicio);
}
