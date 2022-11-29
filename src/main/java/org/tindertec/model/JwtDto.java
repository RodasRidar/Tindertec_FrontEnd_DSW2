package org.tindertec.model;

import java.util.Collection;

import lombok.Data;

@Data
public class JwtDto{
	 private String token;
	  private String bearer;
	    private String nombreUsuario;
	    private Collection<authorities> authorities;

	   
}
