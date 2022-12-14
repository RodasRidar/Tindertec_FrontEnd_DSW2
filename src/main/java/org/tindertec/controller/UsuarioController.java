package org.tindertec.controller;

import org.tindertec.model.Usuario;
import org.tindertec.service.UsuarioService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("Usuario/Registrar/Bot")

	public String cargarregistrarUsuarioBot(Model model, @ModelAttribute Usuario usuario,
			@RequestParam(name = "name", required = false) String nombre,
			@RequestParam(name = "desc", required = false) String descripcion,
			@RequestParam(name = "date", required = false) String fecha,
			@RequestParam(name = "g", required = false) int genero ,
			@RequestParam(name = "i", required = false)  int genInteres,
			@RequestParam(name = "s", required = false) int sede,
			@RequestParam(name = "c", required = false) int carrera,
			@RequestParam(name = "e", required = false) String email
			) {
		
		usuario.setCod_carrera(carrera);
		usuario.setCod_genero(genero);
		usuario.setCod_interes(genInteres);
		usuario.setCod_sede(sede);
		usuario.setFecha_naci(fecha);
		usuario.setDescripcion(descripcion);
		usuario.setNombres(nombre);
		usuario.setEmail(email);
		
		model.addAttribute("lstSedes", usuarioService.listadoSedes().getBody());
		model.addAttribute("lstCarreras", usuarioService.listadoCarreras().getBody());
		model.addAttribute("lstGeneros", usuarioService.listadoGeneros().getBody());
		model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());
		model.addAttribute("usuario", usuario);
		model.addAttribute("inteSelect", genInteres);
		model.addAttribute("carreraSelect", carrera);
		model.addAttribute("sedeSelect", sede);
		model.addAttribute("generoSelect", genero);
		
		


		return "Usuario/RegistroUsuario";
	}
	
	@GetMapping("usuario/registrar")
	public String cargarregistrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		model.addAttribute("lstSedes", usuarioService.listadoSedes().getBody());
		model.addAttribute("lstCarreras", usuarioService.listadoCarreras().getBody());
		model.addAttribute("lstGeneros", usuarioService.listadoGeneros().getBody());
		model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

		return "Usuario/RegistroUsuario";
	}
	
	@PostMapping("usuario/registrar")
	public String registrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		try {
			usuarioService.registrarUsuario(usuario);
			model.addAttribute("msjConfirmation",
					"??Se registro el usuario " + usuario.getNombres() + " correctamente!");
			return "Login/Login";
		} catch (Exception e) {
			model.addAttribute("lstSedes", usuarioService.listadoSedes().getBody());
			model.addAttribute("lstCarreras", usuarioService.listadoCarreras().getBody());
			model.addAttribute("lstGeneros", usuarioService.listadoGeneros().getBody());
			model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

			model.addAttribute("msjConfirmation", "Ups!,Ocurrio un problema en el registro");
			return "Usuario/RegistroUsuario";
		}
	}
	
	@GetMapping("Perfil")
	public String cargarPerfil(Model model, @ModelAttribute Usuario usuario,HttpSession session) throws ParseException {
		Usuario user = (Usuario) session.getAttribute("userInSesion");
		
		String nombresYedad = user.getNombres()+", "+obtenerEdad(user.getFecha_naci());
		String foto1 = user.getFoto1();
		int CodUsuInSession = user.getCod_usu();
		
		System.out.println(user.toString());
		
		Usuario currentUsus = usuarioService.BuscarUsuario(CodUsuInSession);
		System.out.println(currentUsus);
		model.addAttribute("nroFotos", 1);
		
		if (currentUsus.getFoto2().length() > 1) {
			model.addAttribute("nroFotos", 2);
		}
		if (currentUsus.getFoto3().length() > 1) {
			model.addAttribute("nroFotos", 3);
		}
		if (currentUsus.getFoto4().length() > 1) {
			model.addAttribute("nroFotos", 4);
		}
		if (currentUsus.getFoto5().length() > 1) {
			model.addAttribute("nroFotos", 5);
		}

		// Galeria
		model.addAttribute("foto1Gal", currentUsus.getFoto1());
		model.addAttribute("foto2Gal", currentUsus.getFoto2());
		model.addAttribute("foto3Gal", currentUsus.getFoto3());
		model.addAttribute("foto4Gal", currentUsus.getFoto4());
		model.addAttribute("foto5Gal", currentUsus.getFoto5());

		// Manter Usuario
		model.addAttribute("nombresUsu", currentUsus.getNombres());
		model.addAttribute("descripcionUsu", currentUsus.getDescripcion());

		model.addAttribute("inteSelect", currentUsus.getCod_interes());
		model.addAttribute("carreraSelect", currentUsus.getCod_carrera());
		model.addAttribute("sedeSelect", currentUsus.getCod_sede());

		model.addAttribute("lstSede", usuarioService.listadoSedes().getBody());
		model.addAttribute("lstCarrera", usuarioService.listadoCarreras().getBody());
		model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

		//Enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "MantenerUsuario/MantenerUsuario";
	}

	@PostMapping("Perfil/Guardar")
	public String guardarUsuario(Model model, @ModelAttribute Usuario usuario,HttpSession session) throws ParseException{
		
		String m ="";
		Usuario user = (Usuario) session.getAttribute("userInSesion");System.out.println(user.toString());
		System.out.println(user.toString());
		String nombresYedad = user.getNombres()+", "+obtenerEdad(user.getFecha_naci());
		String foto1 = user.getFoto1();
		int CodUsuInSession = user.getCod_usu();

			usuario.setCod_usu(CodUsuInSession);
			m = usuarioService.editarUsuario(usuario);

			model.addAttribute("msjConfirmacionEditarPerfil", m);
			// Manter Usuario
			model.addAttribute("nombresUsu", usuario.getNombres());
			model.addAttribute("descripcionUsu", usuario.getDescripcion());
			
			model.addAttribute("lstSede", usuarioService.listadoSedes().getBody());
			model.addAttribute("lstCarrera", usuarioService.listadoCarreras().getBody());
			model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());
			// options
			model.addAttribute("inteSelect", usuario.getCod_interes());
			model.addAttribute("carreraSelect", usuario.getCod_carrera());
			model.addAttribute("sedeSelect", usuario.getCod_sede());

			// first particion
			model.addAttribute("nombresYedad",nombresYedad);
			model.addAttribute("f1", foto1);


		// Galeria
		Usuario currentUsu = usuarioService.BuscarUsuario(CodUsuInSession);
		model.addAttribute("nroFotos", 1);
		if (currentUsu.getFoto2().length() > 1) {
			model.addAttribute("nroFotos", 2);
		}
		if (currentUsu.getFoto3().length() > 1) {
			model.addAttribute("nroFotos", 3);
		}
		if (currentUsu.getFoto4().length() > 1) {
			model.addAttribute("nroFotos", 4);
		}
		if (currentUsu.getFoto5().length() > 1) {
			model.addAttribute("nroFotos", 5);
		}
		model.addAttribute("foto1Gal", currentUsu.getFoto1());
		model.addAttribute("foto2Gal", currentUsu.getFoto2());
		model.addAttribute("foto3Gal", currentUsu.getFoto3());
		model.addAttribute("foto4Gal", currentUsu.getFoto4());
		model.addAttribute("foto5Gal", currentUsu.getFoto5());
		return "MantenerUsuario/MantenerUsuario";
	}


	@PostMapping("Perfil/AgregarFoto")
	public String agregarFoto(@ModelAttribute Usuario usuario, Model model,HttpSession session,
			@RequestParam(name = "url_foto", required = true) String url_foto) throws ParseException {

		Usuario user = (Usuario) session.getAttribute("userInSesion");
		String nombresYedad = user.getNombres()+", "+obtenerEdad(user.getFecha_naci());
		String foto1 = user.getFoto1();
		int CodUsuInSession = user.getCod_usu();

		try {
			Usuario currentUsu;
			int fotoPosicion = 1;
			currentUsu = usuarioService.BuscarUsuario(CodUsuInSession);
			if (currentUsu.getFoto1().length() > 1) {
				fotoPosicion = 2;
			}
			if (currentUsu.getFoto2().length() > 1) {
				fotoPosicion = 3;
			}
			if (currentUsu.getFoto3().length() > 1) {
				fotoPosicion = 4;
			}
			if (currentUsu.getFoto4().length() > 1) {
				fotoPosicion = 5;
			}
			
			usuarioService.agregarFoto(CodUsuInSession, fotoPosicion, url_foto);
			model.addAttribute("msjConfirmacionAddFoto", "??Foto agregada exitosamente!");

			// Manter Usuario
			model.addAttribute("nombresUsu", currentUsu.getNombres());
			model.addAttribute("descripcionUsu", currentUsu.getDescripcion());
			// options
			model.addAttribute("inteSelect", currentUsu.getCod_interes());
			model.addAttribute("carreraSelect", currentUsu.getCod_carrera());
			model.addAttribute("sedeSelect", currentUsu.getCod_sede());
			
			model.addAttribute("lstSede", usuarioService.listadoSedes().getBody());
			model.addAttribute("lstCarrera", usuarioService.listadoCarreras().getBody());
			model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

			// first particion
			model.addAttribute("nombresYedad",
					currentUsu.getNombres() + "," + obtenerEdad(currentUsu.getFecha_naci()));
			model.addAttribute("f1", currentUsu.getFoto1());

			model.addAttribute("nroFotos", 1);
			if (fotoPosicion == 2) {
				model.addAttribute("nroFotos", 2);
				model.addAttribute("foto1Gal", currentUsu.getFoto1());
				model.addAttribute("foto2Gal", url_foto);
			}
			if (fotoPosicion == 3) {
				model.addAttribute("nroFotos", 3);
				model.addAttribute("foto1Gal", currentUsu.getFoto1());
				model.addAttribute("foto2Gal", currentUsu.getFoto2());
				model.addAttribute("foto3Gal", url_foto);
			}
			if (fotoPosicion == 4) {
				model.addAttribute("nroFotos", 4);
				model.addAttribute("foto1Gal", currentUsu.getFoto1());
				model.addAttribute("foto2Gal", currentUsu.getFoto2());
				model.addAttribute("foto3Gal", currentUsu.getFoto3());
				model.addAttribute("foto4Gal", url_foto);
			}
			if (fotoPosicion == 5) {
				model.addAttribute("nroFotos", 5);
				model.addAttribute("foto1Gal", currentUsu.getFoto1());
				model.addAttribute("foto2Gal", currentUsu.getFoto2());
				model.addAttribute("foto3Gal", currentUsu.getFoto3());
				model.addAttribute("foto4Gal", currentUsu.getFoto4());
				model.addAttribute("foto5Gal", url_foto);
			}

		} catch (Exception e) {

			model.addAttribute("msjConfirmacionAddFoto", "Ocurrio un error al a??adir tu foto");

			model.addAttribute("lstSede", usuarioService.listadoSedes().getBody());
			model.addAttribute("lstCarrera", usuarioService.listadoCarreras().getBody());
			model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

			model.addAttribute("nombresYedad", nombresYedad);
			model.addAttribute("f1", foto1);

			Usuario currentUsus = usuarioService.BuscarUsuario(CodUsuInSession);

			model.addAttribute("inteSelect", currentUsus.getCod_interes());
			model.addAttribute("carreraSelect", currentUsus.getCod_carrera());
			model.addAttribute("sedeSelect", currentUsus.getCod_sede());

			model.addAttribute("nroFotos", 1);
			if (currentUsus.getFoto2().length() > 1) {
				model.addAttribute("nroFotos", 2);
			}
			if (currentUsus.getFoto3().length() > 1) {
				model.addAttribute("nroFotos", 3);
			}
			if (currentUsus.getFoto4().length() > 1) {
				model.addAttribute("nroFotos", 4);
			}
			if (currentUsus.getFoto5().length() > 1) {
				model.addAttribute("nroFotos", 5);
			}

			// Galeria
			model.addAttribute("foto1Gal", currentUsus.getFoto1());
			model.addAttribute("foto2Gal", currentUsus.getFoto2());
			model.addAttribute("foto3Gal", currentUsus.getFoto3());
			model.addAttribute("foto4Gal", currentUsus.getFoto4());
			model.addAttribute("foto5Gal", currentUsus.getFoto5());
		}

		return "MantenerUsuario/MantenerUsuario";
	}
	
	//@Transactional
	@PostMapping("/Perfil/eliminar")
	public String eliminarUsuario(@ModelAttribute Usuario usuario, Model model,HttpSession session) {
		Usuario use = (Usuario) session.getAttribute("userInSesion");
		int CodUsuInSession = use.getCod_usu();
		try {
			Usuario user=new Usuario();
			user.setCod_usu(CodUsuInSession);
			String msj=usuarioService.eliminar(user);
			model.addAttribute("msjConfirmation", msj);
			return "Login/Login";
		} catch (Exception e) {
			
			model.addAttribute("msjConfirmacionAddFoto", "Error al eliminar usuario");
			return "MantenerUsuario/MantenerUsuario";
		}
	}
	public String obtenerEdad(String fecna) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
		// fecna= repoUsua.findById(CodUsuInSession).getFecha_naci();
		Date fechaNacimiento = sdf.parse(fecna);
		Date secondDate = sdf.parse("2022-01-01");

		long diff = (secondDate.getTime() - fechaNacimiento.getTime()) / 365;

		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
		String age;
		age = diffrence + "";

		return age;
	}

}
