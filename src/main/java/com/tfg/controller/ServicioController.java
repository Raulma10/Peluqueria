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
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/servicios")
public class ServicioController {

	private static final String plantillaCreacion = "creacionservicio";
	private static final String plantillaBorrado = "borrarservicio";
	private static final String plantillaListado = "verservicios";
	
	@Autowired
	@Qualifier("ServicioServiceImpl")
	private ServicioService servicioService;
	
	//ACCESO AL FORMULARIO PARA CREAR UN SERVICIO
	@GetMapping("/crearservicio")
	public String mostrarFormulario(Model model) {
		model.addAttribute("servicios",servicioService.listarServicios());
		model.addAttribute("servicio",new Servicio());
		return plantillaCreacion;
	}
	@PostMapping("/crearservicio")
	public String guardarUsuario(@ModelAttribute Servicio servicio) {
		servicioService.crearServicio(servicio);
		return plantillaCreacion;
	}
	
	//ACCESO AL FORMULARIO PARA BORRAR SERVICIO
	@GetMapping("/borrarservicio")
	public String mostrarForm(Model model) {
		model.addAttribute("servicios",servicioService.listarServicios());
		return plantillaBorrado;
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
		return plantillaListado;
	}
}
