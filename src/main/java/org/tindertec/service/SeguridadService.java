package org.tindertec.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.JwtDto;
import org.tindertec.model.Usuario;


@Service
public class SeguridadService {
	
	public  static final  String END_POINT="http://localhost:8081/rest/";
	public  static final  String END_POINTSECURITY="http://localhost:8081/";
	
	public JwtDto Login(Usuario usuario) {
		
		//Consumiendo servicio
		String uri=END_POINTSECURITY+"auth/login";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Usuario> request =new HttpEntity<Usuario>(usuario,headers);
	    RestTemplate restTemplate = new RestTemplate();
	    JwtDto token = restTemplate.postForObject(uri, request, JwtDto.class);
	    
	    return token;
	}
	public String VerificarCredenciales(Usuario usuario) {
		//Consumiendo servicio
		String uri=END_POINT+"login/verificar";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Usuario> request =new HttpEntity<Usuario>(usuario,headers);
	    RestTemplate restTemplate = new RestTemplate();
	    String user = restTemplate.postForObject(uri, request, String.class);
	    
	    return user;
	}
}
