package com.tfg.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfg.entity.Cita;
import com.tfg.entity.Servicio;
import com.tfg.entity.Usuario;
import com.tfg.service.CitaService;
import com.tfg.service.ServicioService;
import com.tfg.service.UsuarioService;




@Controller
@RequestMapping("/citas")
public class CitaController {
    
    @Autowired
    @Qualifier("CitaServiceImpl")
    private CitaService citaService;

    @Autowired
    @Qualifier("ServicioServiceImpl")
    private ServicioService servicioService;

    @Autowired
    @Qualifier("UsuarioServiceImpl")
    private UsuarioService usuarioService;
    
    @GetMapping("/reservarcita")
    public String formularioReservaCita(@RequestParam(required=false) LocalDate fecha, Model model, Principal principal) {
        Optional<Usuario> usuario=usuarioService.buscarPorMail(principal.getName());
        if(usuario.isEmpty()){
            model.addAttribute("error","Usuario no encontrado");
            return "reservarcita";
        }
        
        Usuario cliente=usuario.get();
        
        Cita cita=new Cita();
        cita.setServicio(new Servicio());
        cita.setCliente(cliente);

        model.addAttribute("cita",cita);
        model.addAttribute("servicios",servicioService.listarServicios());
        model.addAttribute("clientes",usuarioService.listarUsuarios());
        
        if(fecha!=null){
            cita.setFecha(fecha);
            List<LocalTime>horasDisponibles=citaService.obtenerHorasDisponibles(fecha);
            model.addAttribute("horasDisponibles", horasDisponibles);
            model.addAttribute("fechaSeleccionada",fecha);

        }
        return "reservarcita";
    }

    @PostMapping("/reservarcita")
    public String reservarCita(@ModelAttribute Cita cita, Model model) {
        
        if(citaService.existeCita(cita.getFecha(),cita.getHoraInicio())){
            model.addAttribute("error","Esa hora ya esta reservada");
            return "reservarcita";
        }

        citaService.crearCita(cita);
        
        return "redirect:/citas/reservarcita";
    }
    
    @GetMapping("/borrarcita")
    public String formularioBorrarCita(@RequestParam(required=false) String nombreCliente,@RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha ,Model model){

        List<Cita> citas=new ArrayList<>();
        List<Usuario>clientes=new ArrayList<>();
        if((nombreCliente==null||nombreCliente.isEmpty())&&fecha==null){
            citas=citaService.listarCitas();
        }else{

            if(nombreCliente!=null && !nombreCliente.isEmpty()){
                usuarioService.buscarPorNombre(nombreCliente).ifPresent(clientes::add);
            }else{
                clientes=usuarioService.listarUsuarios();
            }
            
            for(Usuario cliente:clientes){
                List<Cita> citasCliente=citaService.listarPorCliente(cliente);
                for(Cita cita : citasCliente){
                    if(fecha==null||cita.getFecha().equals(fecha)){
                        citas.add(cita);
                    }
                }
            }
        }

        model.addAttribute("citas",citas);
        model.addAttribute("clientes", usuarioService.listarUsuarios());
        model.addAttribute("fechaSeleccionada",fecha);
        model.addAttribute("clienteBuscado",nombreCliente);

        return "borrarcita";
    }

    @PostMapping("/borrarcita")
    public String borrarCita(@RequestParam int id){
        citaService.borrarCita(id);
        return "redirect:/citas/borrarcita";
    }

    @GetMapping("/miscitas")
    public String verMisCitas(Model model, Principal principal) {
        Optional<Usuario>usuario=usuarioService.buscarPorMail(principal.getName());
        if(usuario.isEmpty()){
            model.addAttribute("error","Usuario no encontrado");
            return "miscitas";
        }
        
        Usuario cliente=usuario.get();

        List<Cita>citasUsuario=citaService.listarPorCliente(cliente);

        model.addAttribute("citas",citasUsuario);
        return "miscitas";
    }

    @PostMapping("/miscitas")
    public String cancelarMiCita(@RequestParam int id, Principal principal) {
        citaService.borrarCita(id);
        return "redirect:/citas/miscitas";
    }

    @GetMapping("/gestionarcita")
    public String gestionarCita(){
        return "gestionarcita";
    }
    
    



    
}
