package org.tindertec.model;


import lombok.Data;


@Data

public class Match {
	//
	

	private int id_match ;
	//private int cod_usu2;
	//private String nombres;	
	//private String foto1;	
	
	

	private String det_match;
	private int cod_usu1 ;


	private Usuario usuario1; 
	
	private int cod_usu2 ;
	private Usuario usuario2;  
	

}
