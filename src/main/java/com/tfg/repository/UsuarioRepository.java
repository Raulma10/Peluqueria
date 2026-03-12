package com.tfg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.entity.Usuario;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	boolean existsByEmail(String email);
	boolean existsByNombre(String nombre);
	boolean existsByApellido(String apellido);
	Optional<Usuario> findByNombre(String nombre);
	List<Usuario> findByApellido(String apellido);
	List<Usuario> findByNombreAndApellido(String nombre, String apellido);
	Optional<Usuario> findByEmail(String email);
}
