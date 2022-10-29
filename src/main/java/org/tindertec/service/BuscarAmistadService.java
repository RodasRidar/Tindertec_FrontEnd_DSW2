package org.tindertec.service;

import java.util.Optional;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.*;

@Service
public class BuscarAmistadService {
	
	/**
	 * @author Jorge
	 */
	
	//Buscar amistad/usuario por id
	public Usuario listaBuscarAmistad(int userId) {
		String uri = SeguridadService.END_POINT+"buscarAmistad/Inicio?idUser="+userId;
		RestTemplate restTemplate = new RestTemplate();
		Usuario usuario = restTemplate.getForObject(uri, Usuario.class);
		
		return usuario;
	}
	
	//Registro de like
	public String like(int CodUsuInSession,int CodUsuarioSeleccionado) {
		String msg="";
		String uri = SeguridadService.END_POINT+"buscarAmistad/Like?CodUsuInSession="+CodUsuInSession+"&CodUsuarioSeleccionado="+CodUsuarioSeleccionado;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		msg = restTemplate.postForObject(uri, request, String.class);
		
		return msg;
	}
	
	//Registro de dislike
	public String disLike(int CodUsuInSession,int CodUsuarioSeleccionado) {
		String msg="";
		String uri = SeguridadService.END_POINT+"buscarAmistad/DisLike?CodUsuInSession="+CodUsuInSession+"&CodUsuarioSeleccionado="+CodUsuarioSeleccionado;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();
		msg = restTemplate.postForObject(uri, request, String.class);
		
		return msg;
	}
	
	
	
	

}
