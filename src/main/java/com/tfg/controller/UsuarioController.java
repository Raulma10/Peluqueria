package com.tfg.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.auth.CustomUserDetails;
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
	
	@GetMapping("/crearusuario")
	public String mostrarFormulario(Model model) {
		model.addAttribute("usuarios",usuarioService.listarUsuarios());
		model.addAttribute("usuario",new Usuario());
		
		return "creacionusuario";
	}

	@PostMapping("/crearusuario")
	public String guardarUsuario(@ModelAttribute Usuario usuario, Model model, Authentication auth) {
		try{
			usuarioService.crearUsuario(usuario);
			boolean esAdmin = auth != null &&
                auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
			if (esAdmin) {
				return "redirect:/usuarios/principal";
			}
			return "redirect:/login";
		}catch(RuntimeException e){
			model.addAttribute("error",e.getMessage());
			return "creacionusuario";
		}
	}

	@GetMapping("/borrarusuario")
	public String mostrarForm(@RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido, @RequestParam(required=false) String correo, Model model) {
		List<Usuario>listaUsuarios=usuarioService.listarUsuarios();
		List<Usuario>usuariosFiltrados=new ArrayList<>();

		for(Usuario usuario:listaUsuarios){
			if(usuario.getRol()==Rol.ADMIN && usuario.getEmail().equals("admin@admin.com")) continue;
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

	@GetMapping("/gestionusuario")
	public String mostrarGestion(@RequestParam(required=false) String nombre, @RequestParam(required=false) String apellido, @RequestParam(required=false) String correo, Model model, Principal principal){
		Usuario usuarioLogueado= usuarioService.buscarPorMail(principal.getName())
				.orElseThrow(()->new RuntimeException("Usuario no encontrado"));
				model.addAttribute("usuarioLogueado",usuarioLogueado);
		
		System.out.println(principal.getName());
		return "gestionusuario";
	}

	@PostMapping("/gestionusuario")
	public String gestionarUsuario(@ModelAttribute Usuario usuario, @RequestParam String accion, Model model, Principal principal){
		
		try{
			if("Guardar Cambios".equalsIgnoreCase(accion)){
				Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuario);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            	CustomUserDetails userDetails = new CustomUserDetails(usuarioActualizado);
            	Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    authentication.getCredentials(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(newAuth);
				return "redirect:/usuarios/principal";
			}else if("Borrar Usuario".equalsIgnoreCase(accion)){
				List<Cita>citas=citaService.listarPorCliente(usuario);
				for(Cita cita:citas){
					citaService.borrarCita(cita.getId());
				}
				usuarioService.borrarUsuario(usuario.getId());
				return "redirect:/logout";
			}
		}catch(RuntimeException e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/logout";
		}
		return "redirect:/logout";
	}

	@GetMapping("/gestionarusuarios")
	public String mostrarGestionar(){
		return "menugestionusuarios";
	}

	@GetMapping("/logout")
	public String mostrarLogout() {
		return "logout";
	}

	@GetMapping("/principal")
	public String mostrarPrincipal(){
		return "principal";
	}
	
	
}
