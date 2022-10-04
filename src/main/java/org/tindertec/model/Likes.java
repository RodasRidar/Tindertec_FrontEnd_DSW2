package org.tindertec.model;


import lombok.Data;


@Data
public class Likes {
	
	private int cod_like; 
	
	private int cod_usu ;
	
	private Usuario usuario; 
	
	private int cod_usu_like;
	
	private Usuario usuario_Like; 
	
	private String det_match ;

	
	
}
