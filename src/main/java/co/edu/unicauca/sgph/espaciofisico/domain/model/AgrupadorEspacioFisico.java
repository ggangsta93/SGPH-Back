package co.edu.unicauca.sgph.espaciofisico.domain.model;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public class AgrupadorEspacioFisico {

	private Long idAgrupadorEspacioFisico;

	private String nombre;

	private String observacion;

	private Facultad facultad;

	public AgrupadorEspacioFisico() {
		//Constructor si parámetros
	}	
	
	public AgrupadorEspacioFisico(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

	public Long getIdAgrupadorEspacioFisico() {
		return idAgrupadorEspacioFisico;
	}

	public void setIdAgrupadorEspacioFisico(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Facultad getFacultad() {
		return facultad;
	}

	public void setFacultad(Facultad facultad) {
		this.facultad = facultad;
	}
}