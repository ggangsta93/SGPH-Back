package co.edu.unicauca.sgph.edificio.domain.model;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public class Edificio {

	private Long idEdificio;

	private Facultad facultad;
	
	private String nombre;
		
	public Edificio() {
		//Constructor sin parámetros
	}

	public Edificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}