package org.tindertec.controller;

import org.tindertec.model.JwtDto;
import org.tindertec.model.Usuario;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tindertec.service.*;

/**
 * @author Richard
 */

@Controller
public class SeguridadController {

	@Autowired
	private UsuarioService usuService;

	@Autowired
	private SeguridadService seguridadS;

	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "Login/Login";
	}

	@GetMapping("/Login")
	public String login2(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "Login/Login";

	}

	@GetMapping("/login")
	public String login3(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "Login/Login";
	}

	@PostMapping("/Ingreso")
	public String validarUsuario(@ModelAttribute Usuario usuario, Model model) throws ParseException {
		try {

			JwtDto tokenRecibio = seguridadS.Login(usuario);

			Usuario user = usuService.BuscarUsuarioEmail(tokenRecibio.getNombreUsuario());

			setSession(user, tokenRecibio.getToken());

			model.addAttribute("usuario", user);

			String edad = obtenerEdad(user.getFecha_naci());
			String nombresYedad = user.getNombres() + "," + edad;
			String foto1 = user.getFoto1();

			model.addAttribute("nombresYedad", nombresYedad);
			model.addAttribute("f1", foto1);

			return "BuscarAmistad/Bienvenida";

		} catch (Exception e) {
			// model.addAttribute("usuario", new Usuario());
			model.addAttribute("msjLogin", "Credenciales Incorrectas");
			return "Login/Login";
		}

	}

	@GetMapping("/LogOut")
	public String logOut(Model model, HttpSession session) {
		model.addAttribute("usuario", new Usuario());
		if (session != null) {
			session.invalidate();
		}
		return "Login/Login";
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
	}

	private void setSession(Usuario userInSesion, String token) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		session.setAttribute("userInSesion", userInSesion);
		session.setAttribute("token", token);

	}
}
