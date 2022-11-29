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
	
	private Integer cod_carrera ;
	

	private Carreras carrera; 
	
	private Integer cod_sede ;
	

	private Sedes sede; 
	
	private Integer cod_interes ;
	

	private InteresGenero interesGenero; 
	
	private Integer cod_genero ;
	

	private GeneroUsuario genero;
	
	
	public Usuario(int cod_usu, String nombres, String email, String clave, String foto1, String fecha_naci,
			String descripcion, int cod_carrera, int cod_sede, int cod_interes, int cod_genero) {
		this.cod_usu = cod_usu;
		this.nombres = nombres;
		this.email = email;
		this.clave = clave;
		this.foto1 = foto1;
		this.fecha_naci = fecha_naci;
		this.descripcion = descripcion;
		this.cod_carrera = cod_carrera;
		this.cod_sede = cod_sede;
		this.cod_interes = cod_interes;
		this.cod_genero = cod_genero;
	}


	public Usuario() {

	}


	public Usuario(String nombres, String email, String clave, String foto1, String fecha_naci, String descripcion,
			int cod_carrera, int cod_sede, int cod_interes, int cod_genero) {
		this.nombres = nombres;
		this.email = email;
		this.clave = clave;
		this.foto1 = foto1;
		this.fecha_naci = fecha_naci;
		this.descripcion = descripcion;
		this.cod_carrera = cod_carrera;
		this.cod_sede = cod_sede;
		this.cod_interes = cod_interes;
		this.cod_genero = cod_genero;
	} 

	
}
