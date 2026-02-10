package com.tfg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.entity.Servicio;

@Repository("ServicioRepository")
public interface ServicioRepository extends JpaRepository<Servicio,Integer>{
	boolean existsByNombre(String nombre);
		
}
