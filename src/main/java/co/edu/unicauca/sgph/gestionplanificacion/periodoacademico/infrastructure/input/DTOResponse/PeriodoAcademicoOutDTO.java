package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.util.Date;

public class PeriodoAcademicoOutDTO implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = 6591217297359674285L;

	private Long idPeriodoAcademico;
	private Long anio;
	private Long periodo;
	private Date fechaInicioPeriodo;
	private Date fechaFinPeriodo;
	private Boolean estado;
	
	public Long getIdPeriodoAcademico() {
		return idPeriodoAcademico;
	}

	public void setIdPeriodoAcademico(Long idPeriodoAcademico) {
		this.idPeriodoAcademico = idPeriodoAcademico;
	}

	public Long getAnio() {
		return anio;
	}

	public void setAnio(Long anio) {
		this.anio = anio;
	}

	public Long getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Long periodo) {
		this.periodo = periodo;
	}

	public Date getFechaInicioPeriodo() {
		return fechaInicioPeriodo;
	}

	public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
		this.fechaInicioPeriodo = fechaInicioPeriodo;
	}

	public Date getFechaFinPeriodo() {
		return fechaFinPeriodo;
	}

	public void setFechaFinPeriodo(Date fechaFinPeriodo) {
		this.fechaFinPeriodo = fechaFinPeriodo;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}