package com.tfg.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.entity.Usuario;

@Repository("UsuarioRepository")
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	boolean existsByEmail(String email);
	Usuario findByEmail(String email);
	
}
