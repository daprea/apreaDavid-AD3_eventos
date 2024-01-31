package eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.modelo.dao.EventoDao;
import eventos.modelo.dao.ReservaDao;
import eventos.modelo.entitis.Evento;
import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/eventos")
public class EventoController {
	
	@Autowired
	EventoDao eventodao;
	
	@Autowired
	ReservaDao redao;
	
	
	@GetMapping("/detalle/{idEvento}")
	public String verEvento(Model model, @PathVariable (name="idEvento") int idEvento) {
		Evento evento = eventodao.buscarEvento(idEvento);
		model.addAttribute("evento", evento);
		return "verDetalle";
	}
		 				
	
	@GetMapping("/eventos/verDetalle")
	public String verDetalles (Model model) {
		model.addAttribute("verDetalle");
	 		return "verDetalle";		
	 }

	@GetMapping("/eventos/verActivos")
	public String verDetalleActivos (Model model) {		
		List<Evento> evento = eventodao.verActivo();
		model.addAttribute("evento", evento);
		return "listaTodosActivoss";
	}
	
	@GetMapping("/eventos/verDestacados")
	public String verDestacados( Model model) {
		
		List<Evento> evento = eventodao.verDestacados();
		model.addAttribute("evento", evento);
		return "destacados";
	}

	@PostMapping("/reservar")
	public String reservarEvento(Model model, @PathVariable ("username") String username, 
			@PathVariable ("idEvento") int  idEvento, @PathVariable ("cantidad") int cantidad, // cantidad de entradas máxima
			HttpSession sesion, RedirectAttributes ratt) {
		
		Evento evento = (Evento) sesion.getAttribute("evento" + idEvento);
		Usuario usuario = (Usuario) sesion.getAttribute(username);
		
		if (usuario != null && evento != null && cantidad <= 10) { // cantidad no puede superar 10 entradas
	        Reserva reserva = new Reserva();
	        reserva.setUsuario(usuario);
	        reserva.setEvento(evento);
	        reserva.setCantidad(cantidad); // 

	        int codigo = redao.reservarEventoV2(reserva);
	        
	        switch (codigo) {
			case 0:
				ratt.addFlashAttribute("mensaje", "Reserva realizada");
				break;
			case 1:
				ratt.addFlashAttribute("mensaje", "No pudo realizarse la reserva: aforo maximo excedido");
				break;
			case 2:
				ratt.addFlashAttribute("mensaje", "ya tenía una reserva para este evento, informar");
				break;
			case 3:
				ratt.addFlashAttribute("mensaje", "No pudo realizarse la reserva: No permitir más de 10 entradas en una reserva");
				break;
			default:
				ratt.addFlashAttribute("mensaje", "Usuario o evento no encontrados");
				break;
	        }
	       
		}
		return "redirect:/home";
	}

}
	



