package org.tindertec.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.Usuario;


@Service
public class SeguridadService {
	
	public  static final  String END_POINT="http://localhost:8081/rest/";
	public Usuario Login(Usuario usuario) {
		//Consumiendo servicio
		String uri=END_POINT+"login";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Usuario> request =new HttpEntity<Usuario>(usuario,headers);
	    RestTemplate restTemplate = new RestTemplate();
	    Usuario user = restTemplate.postForObject(uri, request, Usuario.class);
	    
	    return user;
	}

}
