package org.tindertec.controller;

import org.tindertec.model.Usuario;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.tindertec.service.*;
/**
 * @author Richard
 */

@Controller
public class SeguridadController {
	public static String nombresYedad;
	public static String foto1;
	public static String edad;
	public static int CodUsuInSession;
	
	@Autowired
	private SeguridadService seguridadS;
	@Autowired
	private UsuarioService usuService;
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
		
		String msj =seguridadS.VerificarCredenciales(usuario) ;
	
	if (msj.toString().equals("OK")) {
		Usuario user =seguridadS.Login(usuario) ;
		model.addAttribute("usuario",user);
		CodUsuInSession=user.getCod_usu();
		edad=obtenerEdad(user.getFecha_naci());
		nombresYedad=user.getNombres()+","+edad;
		foto1=user.getFoto1();
		model.addAttribute("nombresYedad",nombresYedad);
		model.addAttribute("f1",foto1);

		return "BuscarAmistad/Bienvenida";
		} 
	else{
		//model.addAttribute("usuario", new Usuario());
		model.addAttribute("msjLogin",msj);
		return "Login/Login";
			
	}
}
	
	@GetMapping("/LogOut")
	public String logOut( Model model) {
		model.addAttribute("usuario",new Usuario());
		
		nombresYedad="";
		foto1="";
		edad="";
		CodUsuInSession=0;
		
		return "Login/Login";
		}
	
	public String obtenerEdad(String fecna) throws ParseException {
		
		 SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
			//fecna= repoUsua.findById(1).get().getFecha_naci();
	        Date fechaNacimiento = sdf.parse(fecna);
	        Date secondDate =  sdf.parse("2022-01-01");

	        long diff = (secondDate.getTime()- fechaNacimiento.getTime())/365;

	        TimeUnit time = TimeUnit.DAYS; 
	        long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);
	        String age;
	        age= diffrence+"";
	        
		return age;
	}
}
