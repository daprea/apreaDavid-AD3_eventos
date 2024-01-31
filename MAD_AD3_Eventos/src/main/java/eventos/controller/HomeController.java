package eventos.controller;

import java.util.List;




	@Controller
	public class HomeController {

	
	
	@GetMapping("/")
	public String verTodos(Model model, Authentication aut) {
		
		return "home";
	} 

	@GetMapping("/clientes/login")
		public String accesoCliente(Model model, Authentication aut) {
			
		
			return "accesoCliente";
	}
}

	@PostMapping("/clientes/login")	
	public String procLogin(HttpSession sesion, @RequestParam("username") String username,
		@RequestParam("password") String password, RedirectAttributes ratt) {
--------------------------------------------------------------------------------


@Controller
public class HomeController {
	@Autowired
	EventoDao edao;
	
	@Autowired
	UsuarioDao udao;
	
	@Autowired
	PerfilDao pdao;
	
	@GetMapping({"", "/", "/home"})
	public String verHome(Model model) {
		List<Evento> evento = edao.buscarTodos();
		model.addAttribute("evento", evento);
		return "home";
	}
	
	@GetMapping("/login")
	public String ingresoUsuario(Model model, HttpSession sesion, Authentication aut) {
		
		return "login";
	}
	
	@PostMapping("/login")	
	public String procLogin(HttpSession sesion, @RequestParam("username") String username,
			@RequestParam("password") String password, RedirectAttributes ratt) {
		/*Paso el "username" al método "login" y lo almaceno en "usuario"  */
		Usuario usuario=udao.login(username, password);
		
		/*Establezco la condición de que, si el usuario existe es admitido.
		 * Caso contrario, se lo redirige al formulario de registro*/
		if (usuario != null ) {
			usuario.setPassword(null);//quitamos el password por seguridad
			sesion.setAttribute("usuario", usuario);
			return "redirect:/";
		}
		ratt.addFlashAttribute("mensaje", "Usuario o password incorrecto");
			return "redirect:/login";
	}	
	
	
	@GetMapping("/registro")
	public String registroUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "registro";
	}
	
	@PostMapping("/registro")	
	public String procRegistro(Usuario usuario, @RequestParam ("username") String username,
			@RequestParam ("password") String password, @RequestParam ("nombre") String nombre, 
			@RequestParam ("apellidos") String apellidos,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,@RequestParam ("direccion") String direccion,
			RedirectAttributes ratt) {
		
		usuario.setUsername(username);
		usuario.setPassword(("{noop}" + password));
		usuario.setNombre(nombre);
		usuario.setApellidos(apellidos);
		usuario.setFechaRegistro(new Date());
		usuario.setDireccion(direccion);
		usuario.addPerfil(pdao.buscarUno(3));
		usuario.setEnabled(1);
		
		if (udao.agregarUno(usuario)==1) {
			ratt.addFlashAttribute("mensaje", "Registrado correctamente");
		return "redirect:/login";
		}
		else
			ratt.addFlashAttribute("mensaje", "Usuario existente");
		return "redirect:/";
		
	}
	
}
