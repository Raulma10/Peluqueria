package com.tfg.service;

import java.util.List;

import com.tfg.entity.Usuario;

public interface UsuarioService {
	
	public abstract List<Usuario> listarUsuarios();
	public abstract Usuario crearUsuario(Usuario usuario);
	public abstract boolean borrarUsuario(int id);
	public abstract Usuario actualizarUsuario(Usuario usuario);
	public abstract Usuario buscarPorMail(String email);
	

}
