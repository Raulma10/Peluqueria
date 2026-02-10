package com.tfg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.repository.UsuarioRepository;
import com.tfg.service.UsuarioService;


@Service("UsuarioServiceImpl")
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	@Qualifier("UsuarioRepository")
	private  UsuarioRepository  usuarioRepository;
	
	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new RuntimeException("El email ya está registrado");
		}
		
		if(usuario.getRol()==null) {
			usuario.setRol(Rol.CLIENTE);
		}
		return usuarioRepository.save(usuario);
	}

	@Override
	public boolean borrarUsuario(int id) {
		listarUsuarios();
		
		if(usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario) {
		if(!usuarioRepository.existsById(usuario.getId())) {
			throw new RuntimeException("Usuario no encontrado");
		}
		
		return usuarioRepository.save(usuario);
	}
	
	@Override
	public Usuario buscarPorMail(String email) {
		return usuarioRepository.findByEmail(email);
	}

}
