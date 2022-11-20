package org.tindertec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.*;
@Service
public class MeGustasService {
	public  static final  String END_POINT="http://localhost:8081/rest/";
	
	public List<Usuario> Listar_Usuarios_Likes(int idUser){
		List<Usuario> lst = null;
		String uri=SeguridadService.END_POINT+"MeGustas?idUser="+idUser;
		RestTemplate restTemplate = new RestTemplate();
		lst = restTemplate.getForObject(uri, List.class);
		return lst;
	}
	
	public String eliminarLike(int CodUsuInSession , int CodUsuarioSeleccionado) {
		String msg="";
		String uri=SeguridadService.END_POINT+"MeGustas/Eliminar?CodUsuInSession="+CodUsuInSession+"&CodUsuarioSeleccionado="+CodUsuarioSeleccionado;
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<String> request =new HttpEntity<String>(headers);
	    RestTemplate restTemplate = new RestTemplate();
	    msg =  restTemplate.postForObject(uri, request, String.class);

		return msg;
	}
}
