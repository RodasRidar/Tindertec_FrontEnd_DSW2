package org.tindertec.controller;

import org.tindertec.model.Usuario;
import org.tindertec.service.UsuarioService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/registrar")
	public String cargarregistrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		model.addAttribute("lstSedes", usuarioService.listadoSedes().getBody());
		model.addAttribute("lstCarreras", usuarioService.listadoCarreras().getBody());
		model.addAttribute("lstGeneros", usuarioService.listadoGeneros().getBody());
		model.addAttribute("lstInteres", usuarioService.listadoIntereses().getBody());

		return "Usuario/RegistroUsuario";
	}
	
	@PostMapping("/registrar")
	public String registrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		try {
			usuarioService.registrarUsuario(usuario);
			model.addAttribute("msjConfirmation",
					"¡Se registro el usuario " + usuario.getNombres() + " correctamente!");
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

	
	/*
	@GetMapping("/Usuario/Registrar")
	public String cargarregistrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		model.addAttribute("lstSedes", repoSed.findAll());
		model.addAttribute("lstCarreras", repoCar.findAll());
		model.addAttribute("lstGeneros", repoGenusu.findAll());
		model.addAttribute("lstInteres", repoInte.findAll());

		return "Usuario/RegistroUsuario";
	}

	@PostMapping("/Usuario/Registrar")
	public String registrarUsuario(Model model, @ModelAttribute Usuario usuario) {

		try {
			repoUsu.USP_USUARIO_REGISTRAR(usuario.getNombres(), usuario.getEmail(), usuario.getFecha_naci(),
					usuario.getClave(), usuario.getCod_sede(), usuario.getCod_carrera(), usuario.getCod_genero(),
					usuario.getCod_interes(), usuario.getDescripcion(), usuario.getFoto1());
			model.addAttribute("msjConfirmation",
					"¡Se registro el usuario " + usuario.getNombres() + " correctamente!");
			return "Login/Login";
		} catch (Exception e) {
			model.addAttribute("lstSedes", repoSed.findAll());
			model.addAttribute("lstCarreras", repoCar.findAll());
			model.addAttribute("lstGeneros", repoGenusu.findAll());
			model.addAttribute("lstInteres", repoInte.findAll());

			model.addAttribute("msjConfirmation", "Ups!,Ocurrio un problema en el registro");
			return "Usuario/RegistroUsuario";
		}
	}

	@GetMapping("/Perfil")
	public String cargarPerfil(Model model, @ModelAttribute Usuario usuario) {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;

		Optional<Usuario> currentUsus = repoUsu.findById(CodUsuInSession);
		System.out.println(currentUsus);
		model.addAttribute("nroFotos", 1);
		if (currentUsus.get().getFoto2().length() > 1) {
			model.addAttribute("nroFotos", 2);
		}
		if (currentUsus.get().getFoto3().length() > 1) {
			model.addAttribute("nroFotos", 3);
		}
		if (currentUsus.get().getFoto4().length() > 1) {
			model.addAttribute("nroFotos", 4);
		}
		if (currentUsus.get().getFoto5().length() > 1) {
			model.addAttribute("nroFotos", 5);
		}

		// Galeria
		model.addAttribute("foto1Gal", currentUsus.get().getFoto1());
		model.addAttribute("foto2Gal", currentUsus.get().getFoto2());
		model.addAttribute("foto3Gal", currentUsus.get().getFoto3());
		model.addAttribute("foto4Gal", currentUsus.get().getFoto4());
		model.addAttribute("foto5Gal", currentUsus.get().getFoto5());

		// Manter Usuario

		model.addAttribute("nombresUsu", currentUsus.get().getNombres());
		model.addAttribute("descripcionUsu", currentUsus.get().getDescripcion());

		model.addAttribute("inteSelect", currentUsus.get().getCod_interes());
		model.addAttribute("carreraSelect", currentUsus.get().getCod_carrera());
		model.addAttribute("sedeSelect", currentUsus.get().getCod_sede());

		model.addAttribute("lstSede", repoSed.findAll());
		model.addAttribute("lstCarrera", repoCar.findAll());
		model.addAttribute("lstInteres", repoInte.findAll());

		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "MantenerUsuario/MantenerUsuario";

	}

	@PostMapping("/Perfil/Guardar")
	public String guardarUsuario(Model model, @ModelAttribute Usuario usuario) {

		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;

		int CodUsuInSession = SeguridadController.CodUsuInSession;

		try {
			repoUsu.USP_EDITAR_PERFIL(CodUsuInSession, usuario.getNombres(), usuario.getDescripcion(),
					usuario.getCod_interes(), usuario.getCod_carrera(), usuario.getCod_sede());
			model.addAttribute("msjConfirmacionEditarPerfil", "Cambios guardados exitosamente.");

			Optional<Usuario> currentUsu = repoUsu.findById(CodUsuInSession);
			System.out.println(currentUsu);
			// Manter Usuario
			model.addAttribute("nombresUsu", currentUsu.get().getNombres());
			model.addAttribute("descripcionUsu", currentUsu.get().getDescripcion());
			// options
			model.addAttribute("inteSelect", currentUsu.get().getCod_interes());
			model.addAttribute("carreraSelect", currentUsu.get().getCod_carrera());
			model.addAttribute("sedeSelect", currentUsu.get().getCod_sede());

			model.addAttribute("lstSede", repoSed.findAll());
			model.addAttribute("lstCarrera", repoCar.findAll());
			model.addAttribute("lstInteres", repoInte.findAll());

			// first particion
			model.addAttribute("nombresYedad",
					currentUsu.get().getNombres() + "," + obtenerEdad(currentUsu.get().getFecha_naci()));
			model.addAttribute("f1", currentUsu.get().getFoto1());

		} catch (Exception e) {
			model.addAttribute("msjConfirmacionEditarPerfil", "Error al guardar cambios.");
			model.addAttribute("lstSede", repoSed.findAll());
			model.addAttribute("lstCarrera", repoCar.findAll());
			model.addAttribute("lstInteres", repoInte.findAll());

			model.addAttribute("nombresYedad", nombresYedad);
			model.addAttribute("f1", foto1);
		}
		Optional<Usuario> currentUsus = repoUsu.findById(CodUsuInSession);
		// Galeria
		model.addAttribute("nroFotos", 1);
		if (currentUsus.get().getFoto2().length() > 1) {
			model.addAttribute("nroFotos", 2);
		}
		if (currentUsus.get().getFoto3().length() > 1) {
			model.addAttribute("nroFotos", 3);
		}
		if (currentUsus.get().getFoto4().length() > 1) {
			model.addAttribute("nroFotos", 4);
		}
		if (currentUsus.get().getFoto5().length() > 1) {
			model.addAttribute("nroFotos", 5);
		}
		model.addAttribute("foto1Gal", currentUsus.get().getFoto1());
		model.addAttribute("foto2Gal", currentUsus.get().getFoto2());
		model.addAttribute("foto3Gal", currentUsus.get().getFoto3());
		model.addAttribute("foto4Gal", currentUsus.get().getFoto4());
		model.addAttribute("foto5Gal", currentUsus.get().getFoto5());
		System.out.println(currentUsus);
		return "MantenerUsuario/MantenerUsuario";
	}

	@PostMapping("/Perfil/AgregarFoto")
	public String agregarFoto(@ModelAttribute Usuario usuario, Model model,
			@RequestParam(name = "url_foto", required = true) String url_foto) {

		int CodUsuInSession = SeguridadController.CodUsuInSession;
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;

		try {
			Optional<Usuario> currentUsu;
			int fotoPosicion = 1;
			currentUsu = repoUsu.findById(CodUsuInSession);
			if (currentUsu.get().getFoto1().length() > 1) {
				fotoPosicion = 2;
			}
			if (currentUsu.get().getFoto2().length() > 1) {
				fotoPosicion = 3;
			}
			if (currentUsu.get().getFoto3().length() > 1) {
				fotoPosicion = 4;
			}
			if (currentUsu.get().getFoto4().length() > 1) {
				fotoPosicion = 5;
			}
			repoUsu.USP_USUARIO_INSERTAR_FOTO(CodUsuInSession, fotoPosicion, url_foto);
			model.addAttribute("msjConfirmacionAddFoto", "¡Foto agregada exitosamente!");

			// Manter Usuario
			model.addAttribute("nombresUsu", currentUsu.get().getNombres());
			model.addAttribute("descripcionUsu", currentUsu.get().getDescripcion());
			// options
			model.addAttribute("inteSelect", currentUsu.get().getCod_interes());
			model.addAttribute("carreraSelect", currentUsu.get().getCod_carrera());
			model.addAttribute("sedeSelect", currentUsu.get().getCod_sede());

			model.addAttribute("lstSede", repoSed.findAll());
			model.addAttribute("lstCarrera", repoCar.findAll());
			model.addAttribute("lstInteres", repoInte.findAll());

			// first particion
			model.addAttribute("nombresYedad",
					currentUsu.get().getNombres() + "," + obtenerEdad(currentUsu.get().getFecha_naci()));
			model.addAttribute("f1", currentUsu.get().getFoto1());

			model.addAttribute("nroFotos", 1);
			if (fotoPosicion == 2) {
				model.addAttribute("nroFotos", 2);
				model.addAttribute("foto1Gal", currentUsu.get().getFoto1());
				model.addAttribute("foto2Gal", url_foto);
			}
			if (fotoPosicion == 3) {
				model.addAttribute("nroFotos", 3);
				model.addAttribute("foto1Gal", currentUsu.get().getFoto1());
				model.addAttribute("foto2Gal", currentUsu.get().getFoto2());
				model.addAttribute("foto3Gal", url_foto);
			}
			if (fotoPosicion == 4) {
				model.addAttribute("nroFotos", 4);
				model.addAttribute("foto1Gal", currentUsu.get().getFoto1());
				model.addAttribute("foto2Gal", currentUsu.get().getFoto2());
				model.addAttribute("foto3Gal", currentUsu.get().getFoto3());
				model.addAttribute("foto4Gal", url_foto);
			}
			if (fotoPosicion == 5) {
				model.addAttribute("nroFotos", 5);
				model.addAttribute("foto1Gal", currentUsu.get().getFoto1());
				model.addAttribute("foto2Gal", currentUsu.get().getFoto2());
				model.addAttribute("foto3Gal", currentUsu.get().getFoto3());
				model.addAttribute("foto4Gal", currentUsu.get().getFoto4());
				model.addAttribute("foto5Gal", url_foto);
			}

		} catch (Exception e) {

			model.addAttribute("msjConfirmacionAddFoto", "Ocurrio un error al añadir tu foto");

			model.addAttribute("lstSede", repoSed.findAll());
			model.addAttribute("lstCarrera", repoCar.findAll());
			model.addAttribute("lstInteres", repoInte.findAll());

			model.addAttribute("nombresYedad", nombresYedad);
			model.addAttribute("f1", foto1);

			Optional<Usuario> currentUsus = repoUsu.findById(CodUsuInSession);

			model.addAttribute("inteSelect", currentUsus.get().getCod_interes());
			model.addAttribute("carreraSelect", currentUsus.get().getCod_carrera());
			model.addAttribute("sedeSelect", currentUsus.get().getCod_sede());

			model.addAttribute("nroFotos", 1);
			if (currentUsus.get().getFoto2().length() > 1) {
				model.addAttribute("nroFotos", 2);
			}
			if (currentUsus.get().getFoto3().length() > 1) {
				model.addAttribute("nroFotos", 3);
			}
			if (currentUsus.get().getFoto4().length() > 1) {
				model.addAttribute("nroFotos", 4);
			}
			if (currentUsus.get().getFoto5().length() > 1) {
				model.addAttribute("nroFotos", 5);
			}

			// Galeria
			model.addAttribute("foto1Gal", currentUsus.get().getFoto1());
			model.addAttribute("foto2Gal", currentUsus.get().getFoto2());
			model.addAttribute("foto3Gal", currentUsus.get().getFoto3());
			model.addAttribute("foto4Gal", currentUsus.get().getFoto4());
			model.addAttribute("foto5Gal", currentUsus.get().getFoto5());
		}

		return "MantenerUsuario/MantenerUsuario";
	}
	
	@Transactional
	@PostMapping("/Perfil/eliminar")
	public String eliminarUsuario(@ModelAttribute Usuario usuario, Model model) {
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		try {
			repoUsu.USP_USUARIO_ELIMINAR(CodUsuInSession);
			model.addAttribute("msjConfirmation", "Usuario eliminado Correctamente");
			return "Login/Login";
		} catch (Exception e) {
			
			model.addAttribute("msjConfirmacionAddFoto", "Error al eliminar usuario");
			return "MantenerUsuario/MantenerUsuario";
		}
		
	}

	public String obtenerEdad(String fecna) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
		// fecna= repoUsua.findById(1).get().getFecha_naci();
		Date fechaNacimiento = sdf.parse(fecna);
		Date secondDate = sdf.parse("2022-01-01");

		long diff = (secondDate.getTime() - fechaNacimiento.getTime()) / 365;

		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
		String age;
		age = diffrence + "";

		return age;
	}*/
}
