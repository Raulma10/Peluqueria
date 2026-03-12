package com.tfg.service;

import java.util.List;
import java.util.Optional;

import com.tfg.entity.Usuario;

public interface UsuarioService {
	
	public abstract List<Usuario> listarUsuarios();
	public abstract Usuario buscarPorId(int id);
	public abstract Optional<Usuario> buscarPorNombre(String nombre);
	public abstract List<Usuario> buscarPorApellido(String apellido);
	public abstract List<Usuario> buscarPorNombreAndApellido(String nombre, String apellido);
	public abstract Optional<Usuario> buscarPorMail(String email);
	public abstract Usuario crearUsuario(Usuario usuario);
	public abstract boolean borrarUsuario(int id);
	public abstract Usuario actualizarUsuario(Usuario usuario);
}
