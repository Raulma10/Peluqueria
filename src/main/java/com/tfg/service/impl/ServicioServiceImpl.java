package com.tfg.service.impl;

import java.util.List;
import java.util.Optional;

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
	public Servicio buscarPorId(int id){
		Optional<Servicio> servicio=servicioRepository.findById(id);
		if(servicio.isPresent()){
			return servicio.get();
		}
		return null;
	}

	@Override
	public List<Servicio> listarServicios() {
		return servicioRepository.findAll();
	}

	@Override
	public Servicio crearServicio(Servicio servicio) {
		if(servicioRepository.existsByNombre(servicio.getNombre())) {
			throw new RuntimeException("El servicio con el nombre "+servicio.getNombre()+" ya está creado");
		}
		
		return servicioRepository.save(servicio);
	}

	@Override
	public boolean borrarServicio(int id) {
		if(servicioRepository.existsById(id)) {
			servicioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Servicio actualizarServicio(Servicio servicio) {
		Servicio servicioBuscado=servicioRepository.findById(servicio.getId()).orElseThrow(()-> new RuntimeException("Servicio no encontrado"));
		
		servicioBuscado.setNombre(servicio.getNombre());
		servicioBuscado.setPrecio(servicio.getPrecio());
		return servicioRepository.save(servicioBuscado);
	}
	
	

}
