package com.tfg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.entity.Usuario;
import com.tfg.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private static final String plantillaCreacion = "creacionusuario";
	private static final String plantillaBorrado = "borrarusuario";
	private static final String plantillaLogin = "login";
	private static final String plantillaPagPrincipal = "principal";
	
	
	@Autowired
	@Qualifier("UsuarioServiceImpl")
	private UsuarioService usuarioService;
	
	//ACCESO A FORMULARIO PARA CREACION DE USUARIO
	@GetMapping("/crearusuario")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuarios",usuarioService.listarUsuarios());
		model.addAttribute("usuario",new Usuario());
		return plantillaCreacion;
	}
	@PostMapping("/crearusuario")
	public String guardarUsuario(@ModelAttribute Usuario usuario) {
		usuarioService.crearUsuario(usuario);
		return plantillaCreacion;
	}
	
	//ACCESO AL FORMULARIO PARA BORRAR USUARIOS
	//A ESTE FORMULARIO SOLO TENDRA ACCESO EL ADMIN
	@GetMapping("/borrarusuario")
	public String mostrarForm(Model model) {
		model.addAttribute("usuarios", usuarioService.listarUsuarios());
		return plantillaBorrado;
	}
	
	@PostMapping("/borrarusuario")
	public String borrarUsuario(@RequestParam int id) {
		usuarioService.borrarUsuario(id);
		return "redirect:/usuarios/borrarusuario";
	}
	
	
	//ACCESO A LA PANTALLA DE INICIO DE SESION
	@GetMapping("/login")
	public String mostrarLogin(Model model) {
		model.addAttribute("usuarios",usuarioService.listarUsuarios());
		
		return plantillaLogin;
	}
	
	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String contraseña, Model model) {
		Usuario usuario =usuarioService.buscarPorMail(email);
		
		//SI NO EXISTE EL CORREO O NO COINCIDE EL CORREO CON LA CONTRASEÑA DE ESE USUARIO SALTA ERROR
		if(usuario==null) {
			model.addAttribute("error", "Email incorrecto");
			return plantillaLogin;
		}
		if(!usuario.getContraseña().equals(contraseña)) {
			model.addAttribute("error","Contraseña Incorrecta");
			return plantillaLogin;
		}
		
		//SI EXISTE, NOS REDIRIGE A LA PAGINA PRINCIPAL
		return plantillaPagPrincipal;
	}
	
	
}
