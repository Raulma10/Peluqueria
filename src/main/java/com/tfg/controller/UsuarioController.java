package com.tfg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.entity.Cita;
import com.tfg.entity.Rol;
import com.tfg.entity.Usuario;
import com.tfg.service.CitaService;
import com.tfg.service.UsuarioService;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	@Qualifier("UsuarioServiceImpl")
	private UsuarioService usuarioService;
	@Autowired
	@Qualifier("CitaServiceImpl")
	private CitaService citaService;
	
	//ACCESO A FORMULARIO PARA CREACION DE USUARIO
	@GetMapping("/crearusuario")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuarios",usuarioService.listarUsuarios());
		model.addAttribute("usuario",new Usuario());
		return "creacionusuario";
	}
	@PostMapping("/crearusuario")
	public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
		try{
			
			usuarioService.crearUsuario(usuario);
			return "redirect:/usuarios/login";
		}catch(RuntimeException e){
			model.addAttribute("error",e.getMessage());
			return "creacionusuario";
		}
	}
	
	//ACCESO AL FORMULARIO PARA BORRAR USUARIOS
	//A ESTE FORMULARIO SOLO TENDRA ACCESO EL ADMIN
	@GetMapping("/borrarusuario")
	public String mostrarForm(@RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido, @RequestParam(required=false) String correo, Model model) {
		List<Usuario>listaUsuarios=usuarioService.listarUsuarios();
		List<Usuario>usuariosFiltrados=new ArrayList<>();

		for(Usuario usuario:listaUsuarios){
			if(usuario.getRol()==Rol.ADMIN) continue;
			boolean coincide=true;

			if(nombre!=null && !nombre.isEmpty()){
				coincide=coincide && usuario.getNombre().trim().equalsIgnoreCase(nombre.trim());
			}
			if(apellido!=null && !apellido.isEmpty()){
				coincide=coincide && usuario.getApellido().trim().equalsIgnoreCase(apellido.trim());
			}
			if(correo!=null && !correo.isEmpty()){
				coincide=coincide && usuario.getEmail().equalsIgnoreCase(correo);
			}
			if(coincide){
				usuariosFiltrados.add(usuario);
			}
		}
		
		if(usuariosFiltrados.isEmpty()){
			model.addAttribute("error","No se ha encontrado ningun usuario");
		}
		model.addAttribute("usuarios", usuariosFiltrados);
		model.addAttribute("nombreClienteBuscado",nombre);
		model.addAttribute("apellidoClienteBuscado",apellido);
		model.addAttribute("correoClienteBuscado",correo);
		return "borrarusuario";
	}
	
	@PostMapping("/borrarusuario")
	public String borrarUsuario(@RequestParam int id) {
		Usuario usuario=usuarioService.buscarPorId(id);
		if(usuario!=null){
			List<Cita>citas=citaService.listarPorCliente(usuario);
			for(Cita cita:citas){
				citaService.borrarCita(cita.getId());
			}
		}
		usuarioService.borrarUsuario(id);
		return "redirect:/usuarios/borrarusuario";
	}
	
	
	//ACCESO A LA PANTALLA DE INICIO DE SESION
	@GetMapping("/login")
	public String mostrarLogin() {
		return "login";
	}

	
	
	@GetMapping("/logout")
	public String mostrarLogout() {
		return "logout";
	}

	//MOSTRAR PAGINA PRINCIPAL
	@GetMapping("/principal")
	public String mostrarPrincipal(){
		return "principal";
	}
	
	
}
