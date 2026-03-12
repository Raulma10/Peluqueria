package com.tfg.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario buscarPorId(int id){
		Optional<Usuario>usuario=usuarioRepository.findById(id);
		if(usuario.isPresent()){
			return usuario.get();
		}
		return null;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> buscarPorNombre(String nombre){
		return usuarioRepository.findByNombre(nombre);
	}

	@Override
	public List<Usuario> buscarPorApellido(String apellido){
		return usuarioRepository.findByApellido(apellido);
	}

	@Override
	public List<Usuario> buscarPorNombreAndApellido(String nombre, String apellido){
		return usuarioRepository.findByNombreAndApellido(nombre,apellido);
	}

	@Override
	public Usuario crearUsuario(Usuario usuario) {
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new RuntimeException("El email ya está registrado");
		}
		
		if(usuario.getRol()==null) {
			usuario.setRol(Rol.CLIENTE);
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
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
	public Optional<Usuario> buscarPorMail(String email) {
		return usuarioRepository.findByEmail(email);
	}

}
