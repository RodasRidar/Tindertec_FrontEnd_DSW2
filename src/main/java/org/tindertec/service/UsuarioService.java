package org.tindertec.service;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.*;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.util.JSONPObject;
@Service
public class UsuarioService {
	
	
	public Usuario  BuscarUsuario(int userId) {
		String uri=SeguridadService.END_POINT+"usuario/obtener?idUsuario="+userId;
		RestTemplate restTemplate = new RestTemplate();
		Usuario user = restTemplate.getForObject(uri, Usuario.class);
		
		return user;
	}
	
	//Listado de Carreras
	public ResponseEntity<Carreras[]> listadoCarreras() {
		String uri=SeguridadService.END_POINT+"usuario/listaCarreras";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Carreras[]> listaCarreras = restTemplate.getForEntity(uri, Carreras[].class);
		
		return listaCarreras;
	}
	
	//Listado de Sedes
	public ResponseEntity<Sedes[]> listadoSedes() {
		String uri=SeguridadService.END_POINT+"usuario/listaSedes";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Sedes[]> listaSedes = restTemplate.getForEntity(uri, Sedes[].class);
		
		return listaSedes;
	}
	
	//Listado de Intereses
	public ResponseEntity<InteresGenero[]> listadoIntereses() {
		String uri=SeguridadService.END_POINT+"usuario/listaInteres";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<InteresGenero[]> listaInteres = restTemplate.getForEntity(uri, InteresGenero[].class);
		
		return listaInteres;
	}
	
	//Listado de Generos
	public ResponseEntity<GeneroUsuario[]> listadoGeneros() {
		String uri=SeguridadService.END_POINT+"usuario/listaGeneros";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<GeneroUsuario[]> listaGeneros = restTemplate.getForEntity(uri, GeneroUsuario[].class);
		
		return listaGeneros;
	}
	
	/*Registro de Usuario
	public Usuario registrarUsuario(Usuario objUsuario){
		String uri=SeguridadService.END_POINT+"usuario/registrar";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Usuario> request =new HttpEntity<Usuario>(objUsuario, headers);
	    RestTemplate restTemplate = new RestTemplate();
	    Usuario usu = restTemplate.postForObject(uri, request, Usuario.class);
	    
	    return usu;
	}*/
	//Registro de Usuario
	public Usuario registrarUsuario(Usuario objUsuario){
		String uri=SeguridadService.END_POINTSECURITY+"auth/nuevo";
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Usuario> request =new HttpEntity<Usuario>(objUsuario, headers);
	    RestTemplate restTemplate = new RestTemplate();
	    Usuario usu = restTemplate.postForObject(uri, request, Usuario.class);
	    
	    return usu;
	}
}
