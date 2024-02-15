package co.edu.unicauca.sgph.programa.domain.model;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import lombok.Data;

@Data
public class Programa {

	private Long idPrograma;

	private String nombre;
	
	private String abreviatura;

	private Facultad facultad;

	private List<Asignatura> asignaturas;	
		
	public Programa() {
		//Constructor sin argumentos
	}

	public Programa(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
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

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}

	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<Asignatura> asignaturas) {
		this.asignaturas = asignaturas;
	}
}