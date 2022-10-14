package org.tindertec.service;

import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.tindertec.model.*;
@Service
public class UsuarioService {
	
	
	public Usuario  BuscarUsuario(int userId) {
		String uri=SeguridadService.END_POINT+"usuario/obtener?idUsuario="+userId;
		RestTemplate restTemplate = new RestTemplate();
		Usuario user = restTemplate.getForObject(uri, Usuario.class);
		
		return user;
	}
}
