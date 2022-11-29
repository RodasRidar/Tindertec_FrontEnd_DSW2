package org.tindertec.controller;

import org.tindertec.model.Chat;
import org.tindertec.model.Match;
import org.tindertec.model.Usuario;
import org.tindertec.service.ChatService;
import org.tindertec.service.UsuarioService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BuscarAmistadController {
	@Autowired
	private ChatService chatS;
	@Autowired
	private UsuarioService usuarioS;
	
	/**
	 * @author Richard
	 */
	public String obtenerEdad(String fecna) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
		// fecna= repoUsua.findById(CodUsuInSession).get().getFecha_naci();
		Date fechaNacimiento = sdf.parse(fecna);
		Date secondDate = sdf.parse("2022-01-01");

		long diff = (secondDate.getTime() - fechaNacimiento.getTime()) / 365;

		TimeUnit time = TimeUnit.DAYS;
		long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
		String age;
		age = diffrence + "";

		return age;
	}

	
	@GetMapping("/BuscarAmistar/Bienvenida")
	public String cargarBienvenida(Model model,HttpSession session) throws ParseException {
		Usuario user=(Usuario) session.getAttribute("userInSesion");
		String nombresYedad = user.getNombres();
		String foto1 = user.getFoto1();
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "BuscarAmistad/Bienvenida";
	}

	@GetMapping("/Chat")
	public String cargarChat(Model model) throws ParseException {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		Chat chat = new Chat();
		List<Match> match=chatS.ListarMatchs(CodUsuInSession);
		
		if (match.isEmpty()) {
			model.addAttribute("msjNULLMatch", 0);
			model.addAttribute("msjNULLMensajes", 0);
		} else {
			// enviar Match
			model.addAttribute("lstMatch", match);

			// enviar chat vacio
			model.addAttribute("lstMensajes", chat);
			model.addAttribute("msjNULLMatch", 1); // post tambien
			model.addAttribute("msjNULLMensajes", 0);
			model.addAttribute("MSJdeleteMatchAndMsj", "");
		}

		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "Chat/Chat";
	}

	@PostMapping("/BuscarAmistad/Chat")
	public String SeleccionarChat(@RequestParam(name = "id", required = true) int id, Model model) {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		List<Match> match;
		Usuario usu2;
		List<Chat> chat;

		chat = chatS.ListarChat(CodUsuInSession, id);
		match=chatS.ListarMatchs(CodUsuInSession);
		usu2 = usuarioS.BuscarUsuario(id);

		if (match.isEmpty()) {
			model.addAttribute("msjNULLMatch", 0);
			model.addAttribute("msjNULLMensajes", 0);
		} else {
			model.addAttribute("msjNULLMatch", 1);
			//model.addAttribute("msjNULLMensajes", 1);

			// enviar Match
			model.addAttribute("lstMatch", match);
			model.addAttribute("foto1", usu2.getFoto1());
			model.addAttribute("nombres", usu2.getNombres());

			if (chat.isEmpty()) {
				model.addAttribute("msjNULLMensajes", 1);
				// body
				model.addAttribute("msjNULLMensajes_2", "¡Tu match esta esperando, enviale un mensaje 📨!");
			
				// head
				String ChatconNombre = "Chat con " + usu2.getNombres();
				model.addAttribute("Chatfoto", usu2.getFoto1());
				model.addAttribute("ChatconNombre", ChatconNombre);
				model.addAttribute("ChatUserid", id);
				// footer
				model.addAttribute("cod_usu_enviarmsj", id);
				model.addAttribute("MSJdeleteMatchAndMsj", "");
			} else {
				model.addAttribute("msjNULLMensajes", 1);
				//model.addAttribute("msjNULLMensajes_2", "¡Tu match esta esperando, enviale un mensaje 📨!");
				// enviar chat
				// head
				String ChatconNombre = "Chat con " + usu2.getNombres();
				model.addAttribute("Chatfoto", usu2.getFoto1());
				model.addAttribute("ChatconNombre", ChatconNombre);
				model.addAttribute("ChatUserid", id);
				// body
				model.addAttribute("lstMensajes", chat);
				model.addAttribute("cod_usu_now", CodUsuInSession);
				model.addAttribute("cod_usu_1_msj", CodUsuInSession);
				model.addAttribute("cod_usu_2_msj", id);
				model.addAttribute("foto1_msj ", usu2.getFoto1());
				// footer
				model.addAttribute("cod_usu_enviarmsj", id);
				model.addAttribute("MSJdeleteMatchAndMsj", "");
			}
		}

		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "Chat/Chat";
	}

	@PostMapping("/BuscarAmistad/EnviarMensaje")
	public String sendMensaje(@RequestParam(name = "msj_enviar", required = true) String msj_enviar,
			@RequestParam(name = "cod_usu_enviarmsj", required = true) int cod_usu_enviarmsj, Model model) {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		
		// enviar msj
		chatS.sendMensaje(CodUsuInSession, cod_usu_enviarmsj, msj_enviar);

		List<Match> match;
		Usuario usu2;
		List<Chat> chat;
		chat = chatS.ListarChat(CodUsuInSession, cod_usu_enviarmsj);
		match=chatS.ListarMatchs(CodUsuInSession);
		usu2 = usuarioS.BuscarUsuario(cod_usu_enviarmsj);

		if (match.isEmpty()) {
			model.addAttribute("msjNULLMatch", 0);
			model.addAttribute("msjNULLMensajes", 0);
		} else {
			model.addAttribute("msjNULLMatch", 1);
			model.addAttribute("msjNULLMensajes", 1);

			// enviar Match
			model.addAttribute("lstMatch", match);
			model.addAttribute("foto1", usu2.getFoto1());
			model.addAttribute("nombres", usu2.getNombres());

			if (chat == null) {
				model.addAttribute("msjNULLMensajes", 0);
			} else {
				model.addAttribute("msjNULLMensajes", 1);
				// enviar chat
				// head
				String ChatconNombre = "Chat con " + usu2.getNombres();
				model.addAttribute("Chatfoto", usu2.getFoto1());
				model.addAttribute("ChatconNombre", ChatconNombre);
				model.addAttribute("ChatUserid", cod_usu_enviarmsj);
				// body
				model.addAttribute("lstMensajes", chat);
				model.addAttribute("cod_usu_now", CodUsuInSession);
				model.addAttribute("cod_usu_1_msj", CodUsuInSession);
				model.addAttribute(cod_usu_enviarmsj);
				model.addAttribute("foto1_msj ", usu2.getFoto1());
				// footer
				model.addAttribute("cod_usu_enviarmsj", cod_usu_enviarmsj);
				model.addAttribute("MSJdeleteMatchAndMsj", "");
			}
		}

		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "Chat/Chat";
	}

	@PostMapping("/BuscarAmistad/CancelarMatch")
	public String CancelarMatch(@RequestParam(name = "cod_usu_menu", required = true) int cod_usu_menu, Model model)

			throws ParseException {

		int CodUsuInSession = SeguridadController.CodUsuInSession;
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		
		// cancelar match
		chatS.CancelarMatch(CodUsuInSession, cod_usu_menu);
		
		Chat chat = new Chat();
		List<Match> match;
		
		match=chatS.ListarMatchs(CodUsuInSession);
	
		
		
		if (match.isEmpty()) {
			model.addAttribute("msjNULLMatch", 0);
			model.addAttribute("msjNULLMensajes", 0);
		} else {
			model.addAttribute("msjNULLMatch", 1);
			model.addAttribute("msjNULLMensajes", 1);

			// enviar Match
			model.addAttribute("lstMatch", match);

			
				model.addAttribute("msjNULLMensajes", 0);
			
				//model.addAttribute("msjNULLMensajes", 1);
				model.addAttribute("lstMensajes", chat);
			
		}
		

		model.addAttribute("MSJdeleteMatchAndMsj", "¡Se ha eliminado el match y los mensajes!");

		
		
		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "Chat/Chat";
	}

	/**
	 * @author Jorge
	 */
	
	/*
 
	@GetMapping("/Inicio")
	public String cargarInicio(Model model) throws ParseException {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		// enviarle el usuario que inicio sesion
		int codigoValidacion = -1;
		Usuario u = repoUsua.listaBuscarAmistad(CodUsuInSession);

		if (u == null) {

			model.addAttribute("codigoValidacion", codigoValidacion);
		} else {
			codigoValidacion = 1;
			model.addAttribute("codigoValidacion", codigoValidacion);
			model.addAttribute("nombreYedadBusAmi", u.getNombres() + " ," + obtenerEdad(u.getFecha_naci()));
			model.addAttribute("fotoBusAmi", u.getFoto1());
			model.addAttribute("codigoBusAmi", u.getCod_usu());
			model.addAttribute("sedeBusAmi", u.getSede().getDes_sede());
			model.addAttribute("carreraBusAmi", u.getCarrera().getDes_carrera());
		}
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);

		return "BuscarAmistad/BuscarAmistad";
	}
	@PostMapping("BuscarAmistad/Like")
	public String like(@ModelAttribute Usuario usu, Model model) throws ParseException {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
		
		int codigoValidacion = -1;
		
		String mensaje ="";
		 mensaje = repoLike.USP_INSERTAR_LIKE(CodUsuInSession, usu.getCod_usu());

		if (mensaje != null) {
			model.addAttribute("mensajeBuscarAmistad", 1);
			codigoValidacion = 1;
			Usuario user = new Usuario();
			user=repoUsua.getOne(usu.getCod_usu());

			model.addAttribute("codigoValidacion", codigoValidacion);
			model.addAttribute("nombreYedadBusAmi", user.getNombres() + " ," + obtenerEdad(user.getFecha_naci()));
			model.addAttribute("fotoBusAmi", user.getFoto1());
			model.addAttribute("codigoBusAmi", user.getCod_usu());
			model.addAttribute("sedeBusAmi",user.getSede().getDes_sede());
			model.addAttribute("carreraBusAmi", user.getCarrera().getDes_carrera());
			
		}
		else {
			Usuario u = repoUsua.listaBuscarAmistad(CodUsuInSession);
			if (u == null) {

				model.addAttribute("codigoValidacion", codigoValidacion);
			} else {
				codigoValidacion = 1;
				model.addAttribute("codigoValidacion", codigoValidacion);
				model.addAttribute("nombreYedadBusAmi", u.getNombres() + " ," + obtenerEdad(u.getFecha_naci()));
				model.addAttribute("fotoBusAmi", u.getFoto1());
				model.addAttribute("codigoBusAmi", u.getCod_usu());
				model.addAttribute("sedeBusAmi", u.getSede().getDes_sede());
				model.addAttribute("carreraBusAmi", u.getCarrera().getDes_carrera());

			}
		}

		// enviarle el usuario que inicio sesion

		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "BuscarAmistad/BuscarAmistad";
	}

	@PostMapping("/BuscarAmistad/disLike")
	public String dislike(@ModelAttribute Usuario usu, Model model) throws ParseException {
		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;
// hacer con @RequestParam , recuperar el parametro enviado por el formulario post
		int codigoValidacion = -1;
		repoDislike.USP_INSERTAR_DISLIKE(CodUsuInSession, usu.getCod_usu());

		// CARD
		Usuario u = repoUsua.listaBuscarAmistad(CodUsuInSession);
		if (u == null) {

			model.addAttribute("codigoValidacion", codigoValidacion);
		} else {
			codigoValidacion = 1;
			model.addAttribute("codigoValidacion", codigoValidacion);
			model.addAttribute("nombreYedadBusAmi", u.getNombres() + " ," + obtenerEdad(u.getFecha_naci()));
			model.addAttribute("fotoBusAmi", u.getFoto1());
			model.addAttribute("codigoBusAmi", u.getCod_usu());
			model.addAttribute("sedeBusAmi", u.getSede().getDes_sede());
			model.addAttribute("carreraBusAmi", u.getCarrera().getDes_carrera());
		}

		// enviarle el usuario que inicio sesion

		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "BuscarAmistad/BuscarAmistad";
	}
*/

/*
	@PostMapping("/BuscarAmistad/VerPerfil")
	public String VerPerfil(@RequestParam(name = "cod_usu_menu", required = false) int cod_usu_menu, Model model)
			throws ParseException {

		///// REDIRECCIONAR A PAGINA VERPERFIL

		String nombresYedad = SeguridadController.nombresYedad;
		String foto1 = SeguridadController.foto1;
		int CodUsuInSession = SeguridadController.CodUsuInSession;

		// enviarle el usuario que inicio sesion
		model.addAttribute("nombresYedad", nombresYedad);
		model.addAttribute("f1", foto1);
		return "Chat/Chat";
	}*/
}
