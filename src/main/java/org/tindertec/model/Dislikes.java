package org.tindertec.model;





import lombok.Data;


@Data
public class Dislikes {
	
	private int cod_dislike ;
	
	private int cod_usu ;

	private Usuario usuario; 
	
	private int cod_usu_dislike;

	private Usuario usuario_diLike; 

}
