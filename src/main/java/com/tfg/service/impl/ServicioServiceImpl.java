package com.tfg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tfg.entity.Servicio;
import com.tfg.repository.ServicioRepository;
import com.tfg.service.ServicioService;



@Service("ServicioServiceImpl")
public class ServicioServiceImpl implements ServicioService{

	@Autowired
	@Qualifier("ServicioRepository")
	private  ServicioRepository  servicioRepository;

	@Override
	public List<Servicio> listarServicios() {
		// TODO Auto-generated method stub
		return servicioRepository.findAll();
	}

	@Override
	public Servicio crearServicio(Servicio servicio) {
		// TODO Auto-generated method stub
		if(servicioRepository.existsByNombre(servicio.getNombre())) {
			throw new RuntimeException("El servicio con el nombre "+servicio.getNombre()+" ya está creado");
		}
		
		return servicioRepository.save(servicio);
	}

	@Override
	public boolean borrarServicio(int id) {
		// TODO Auto-generated method stub
		if(servicioRepository.existsById(id)) {
			servicioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Servicio actualizarServicio(Servicio servicio) {
		// TODO Auto-generated method stub
		if(!servicioRepository.existsById(servicio.getId())) {
			throw new RuntimeException("Servicio no encontrado");
		}
		return servicioRepository.save(servicio);
	}
	
	

}
