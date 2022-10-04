package org.tindertec.controller;

import org.tindertec.model.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
/**
 * @author Richard
 */
@Controller
public class SeguridadController {
	public static String nombresYedad;
	public static String foto1;
	public static String edad;
	public static int CodUsuInSession;

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

		//Consumiendo servicio
		String uri="http://localhost:8081/rest/login?u="+usuario.getEmail()+"&c="+usuario.getClave();
		RestTemplate restTemplate = new RestTemplate();
		String msj =restTemplate.getForObject(uri, String.class);
		
	if (msj.equals("OK")) {
		Usuario u = new Usuario();
		model.addAttribute("usuario",u);
		CodUsuInSession=u.getCod_usu();
		//edad=obtenerEdad(repoUsua.findById(u.getCod_usu()).get().getFecha_naci());
		//nombresYedad=repoUsua.findById(u.getCod_usu()).get().getNombres()+","+edad;
		//foto1=repoUsua.findById(u.getCod_usu()).get().getFoto1();
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
	/*
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
	}*/
}
