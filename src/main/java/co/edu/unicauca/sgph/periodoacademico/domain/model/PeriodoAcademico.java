package co.edu.unicauca.sgph.periodoacademico.domain.model;

import java.util.Date;

import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.EstadoPeriodoAcademicoEnum;

public class PeriodoAcademico {
	private Long idPeriodoAcademico;
	private Long anio;
	private Long periodo;
	private Date fechaInicioPeriodo;
	private Date fechaFinPeriodo;
	private EstadoPeriodoAcademicoEnum estado;
	
	public PeriodoAcademico() {
		//Constructor sin parametros
	}
	
	public PeriodoAcademico(Long idPeriodoAcademico) {
		this.idPeriodoAcademico = idPeriodoAcademico;
	}

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

	public EstadoPeriodoAcademicoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoPeriodoAcademicoEnum estado) {
		this.estado = estado;
	}
}