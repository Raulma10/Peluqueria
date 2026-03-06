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

import com.tfg.entity.Servicio;
import com.tfg.service.ServicioService;



@Controller
@RequestMapping("/servicios")
public class ServicioController {
	
	@Autowired
	@Qualifier("ServicioServiceImpl")
	private ServicioService servicioService;
	
	//ACCESO AL FORMULARIO PARA CREAR UN SERVICIO
	@GetMapping("/crearservicio")
	public String mostrarFormulario(Model model) {
		model.addAttribute("servicios",servicioService.listarServicios());
		model.addAttribute("servicio",new Servicio());
		return "creacionservicio";
	}
	@PostMapping("/crearservicio")
	public String guardarServicio(@ModelAttribute Servicio servicio, Model model) {
		try{
			servicioService.crearServicio(servicio);
			return "redirect:/servicios/crearservicio";
		}catch(RuntimeException e){
			model.addAttribute("error",e.getMessage());
			return "creacionservicio";
		}
	}

	//FORMULARIO PARA ACRTUALIZAR SERVICIO
	@GetMapping("/actualizarservicio")
	public String mostrarServicios(@RequestParam(value="idServicioSeleccionado", required = false) Integer idServicioSeleccionado,Model model){
		model.addAttribute("servicios",servicioService.listarServicios());
		if(idServicioSeleccionado!=null){
			Servicio servicioSeleccionado=servicioService.buscarPorId(idServicioSeleccionado);
			model.addAttribute("servicioSeleccionado",servicioSeleccionado);
		}
		return "actualizarservicio";
	}

	@PostMapping("/actualizarservicio")
	public String actualizarServicio(@ModelAttribute Servicio servicio, Model model){
		try{
			servicioService.actualizarServicio(servicio);
		}catch(RuntimeException e){
			model.addAttribute("error",e.getMessage());
			model.addAttribute("servicios",servicioService.listarServicios());
			model.addAttribute("servicioSeleccionado",servicio);
			return "actualizarservicio";
		}
		return "redirect:/servicios/actualizarservicio";
	}
	
	//ACCESO AL FORMULARIO PARA BORRAR SERVICIO
	@GetMapping("/borrarservicio")
	public String mostrarForm(Model model) {
		model.addAttribute("servicios",servicioService.listarServicios());
		return "borrarservicio";
	}
	
	@PostMapping("/borrarservicio")
	public String borrarServicio(@RequestParam int id) {
		servicioService.borrarServicio(id);
		return "redirect:/servicios/borrarservicio";
	} 
	
	//ACCESO A LISTA DE SERVICIOS
	@GetMapping("/verservicios")
	public String verServicios(Model model) {
		model.addAttribute("servicios",servicioService.listarServicios());
		return "verservicios";
	}

	@GetMapping("/gestionarservicios")
	public String mostrarGestionar(){
		return "gestionarservicios";
	}
}
