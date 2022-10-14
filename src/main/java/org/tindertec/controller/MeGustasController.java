package org.tindertec.controller;

import org.tindertec.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tindertec.model.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tindertec.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
///
@Controller
public class MeGustasController {

	
	/*
	
	@GetMapping("/MeGustas")
	public String cargarMegustas(Model model) {

		// enviarle el usuario que inicio sesion
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;

		List<Usuario> lstusu = usuRepo.USP_Listar_Usuarios_Likes(CodUsuInSession);

		if (lstusu.isEmpty()) {
			model.addAttribute("msjEliminarLike", "!No has dado ningun â�¤, ve busca amistades!");
		} else {
			
			model.addAttribute("listaUsuarios", lstusu);
		}
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		model.addAttribute("listaUsuarios", lstusu);
		return "MeGustas/MeGustas";
	}

	@PostMapping("/MeGustas/Eliminar")
	public String ProcesarMegustas(@ModelAttribute Usuario usuario, Model model) {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		System.out.println(usuario);
		// enviarle el usuario que inicio sesion

		disLikesRepo.USP_ELIMINAR_LIKE(CodUsuInSession, usuario.getCod_usu());
		// model.addAttribute("mensajeSucess", "Eliminado");

		if (usuRepo.USP_Listar_Usuarios_Likes(CodUsuInSession).isEmpty()) {

			model.addAttribute("msjEliminarLike", " ðŸ“Œ !Ya no hay mas me gustas que ver!");
		} else {
			// model.addAttribute("msjEliminarLike","hay usuarios");
			model.addAttribute("listaUsuarios", usuRepo.USP_Listar_Usuarios_Likes(CodUsuInSession));
		}

		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "MeGustas/MeGustas";
	}
	*/
}
