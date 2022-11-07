package org.tindertec.model;


import lombok.Data;


@Data
public class Chat {

	private int cod_chat;
	private int cod_usu1 ;

	private Usuario usuario1; 
	
	private int cod_usu2 ;

	private Usuario usuario2; 
	
	
	private String mensaje ;
	private String fecha ;
	

	
}
