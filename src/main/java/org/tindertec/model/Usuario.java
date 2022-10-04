package org.tindertec.model;


import lombok.Data;
@Data


public class Usuario {
	
	private int cod_usu  ;
	private String nombres ;
	private String email ;
	private String clave ;
	private String foto1 ;
	private String foto2 ;
	private String foto3 ;
	private String foto4 ;
	private String foto5 ;
	private String fecha_naci ;
	private String descripcion ;
	
	private int cod_carrera ;
	
	private Carreras carrera; 
	
	
	private int cod_sede ;

	private Sedes sede; 
	
	
	private int cod_interes ;

	private InteresGenero interesGenero; 
	
	
	private int cod_genero ;

	private GeneroUsuario genero; 
	
	
	private int cod_suscrip ;

	private Suscripcion suscripcion; 
	
	
}
