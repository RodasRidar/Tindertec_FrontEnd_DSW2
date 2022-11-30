package org.tindertec.controller;

import org.tindertec.model.Usuario;
import org.tindertec.service.MeGustasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping()
public class MeGustasController {
	@Autowired
	private MeGustasService service;

	@GetMapping("/MeGustas")
	public String cargarMegustas(Model model,HttpSession session) throws ParseException {

		// enviarle el usuario que inicio sesion
		Usuario user = (Usuario) session.getAttribute("userInSesion");
		String nombresYedad = user.getNombres()+", "+obtenerEdad(user.getFecha_naci());
		String foto1 = user.getFoto1();
		int CodUsuInSession = user.getCod_usu();

		List<Usuario> lstusu = service.Listar_Usuarios_Likes(CodUsuInSession);

		if (lstusu.isEmpty()) {
			model.addAttribute("msjEliminarLike", "!No has dado ningun ❤️, ve busca amistades!");
		} else {

			model.addAttribute("listaUsuarios", lstusu);
		}
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		model.addAttribute("listaUsuarios", lstusu);
		return "MeGustas/MeGustas";
	}

	@PostMapping("/MeGustas/Eliminar")
	public String ProcesarMegustas(@ModelAttribute Usuario usuario, Model model,HttpSession session) throws ParseException {
		Usuario user = (Usuario) session.getAttribute("userInSesion");
		String nombresYedad = user.getNombres()+", "+obtenerEdad(user.getFecha_naci());
		String foto1 = user.getFoto1();
		int CodUsuInSession = user.getCod_usu();
		System.out.println(usuario);
		// enviarle el usuario que inicio sesion

		service.eliminarLike(CodUsuInSession, usuario.getCod_usu());
		// model.addAttribute("mensajeSucess", "Eliminado");

		if (service.Listar_Usuarios_Likes(CodUsuInSession).isEmpty()) {

			model.addAttribute("msjEliminarLike", " 📌 !Ya no hay mas me gustas que ver!");
		} else {
			// model.addAttribute("msjEliminarLike","hay usuarios");
			model.addAttribute("listaUsuarios", service.Listar_Usuarios_Likes(CodUsuInSession));
		}

		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "MeGustas/MeGustas";
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
