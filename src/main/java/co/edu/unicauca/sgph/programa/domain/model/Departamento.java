package co.edu.unicauca.sgph.programa.domain.model;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import lombok.Data;

@Data
public class Departamento {

	private Long idDepartamento;

	private String nombre;

	private Facultad facultad;

	public Departamento() {
		// Constructor sin argumentos
	}

	public Departamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}
}