package org.tindertec.service;

import java.util.List;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.*;

@Service
public class ChatService {
	
	
	public List<Match> ListarMatchs(int CodUsuInSession) {

		//Consumiendo servicio
		String uri=SeguridadService.END_POINT+"buscarAmistad/Chat?idUsuario="+CodUsuInSession;
		RestTemplate restTemplate = new RestTemplate();
		List<Match> match = restTemplate.getForObject(uri, List.class);
		return match;
	}
	
	public List<Chat> ListarChat(int CodUsuInSession,int CodUsuarioSeleccionado) {
		String uri=SeguridadService.END_POINT+"buscarAmistad/CargarChat?CodUsuInSession="+CodUsuInSession+"&CodUsuarioSeleccionado="+CodUsuarioSeleccionado;
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<List<Chat>> request =new HttpEntity<List<Chat>>(headers);
	    RestTemplate restTemplate = new RestTemplate();
	    List<Chat> chat = (List<Chat>) restTemplate.postForObject(uri, request, List.class);
	    
	    return chat;
	}
	
	public String sendMensaje(int CodUsuInSession,int cod_usu_enviarmsj,String msj_enviar) {
		String msg="";
		String uri=SeguridadService.END_POINT+"buscarAmistad/EnviarMensaje?CodUsuInSession="+CodUsuInSession+"&msj_enviar="+msj_enviar+"&cod_usu_enviarmsj="+cod_usu_enviarmsj;
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<String> request =new HttpEntity<String>(headers);
	    RestTemplate restTemplate = new RestTemplate();
	    msg =  restTemplate.postForObject(uri, request, String.class);

		return msg;
		
	}
	
	public String CancelarMatch(int CodUsuInSession, int cod_usu_menu) {
		String msg="";
		String uri=SeguridadService.END_POINT+"buscarAmistad/CancelarMatch?CodUsuInSession="+CodUsuInSession+"&cod_usu_menu="+cod_usu_menu;
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<String> request =new HttpEntity<String>(headers);
	    RestTemplate restTemplate = new RestTemplate();
	    msg =  restTemplate.postForObject(uri, request, String.class);

		return msg;
		
	}
}
