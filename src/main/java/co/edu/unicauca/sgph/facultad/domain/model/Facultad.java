package co.edu.unicauca.sgph.facultad.domain.model;

import lombok.Data;

@Data
public class Facultad {

	private Long idFacultad;

	private String nombre;

	private String abreviatura;
	
	public Facultad() {

	}
	
	public Facultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
}