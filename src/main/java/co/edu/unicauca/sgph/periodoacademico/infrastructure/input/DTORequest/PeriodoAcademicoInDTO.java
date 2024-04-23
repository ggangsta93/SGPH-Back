package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation.ExistsByAnioAndPeriodo;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation.FechaFinPeriodoGreaterThanFechaInicioPeriodo;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.EstadoPeriodoAcademicoEnum;

@ExistsByAnioAndPeriodo
@FechaFinPeriodoGreaterThanFechaInicioPeriodo
public class PeriodoAcademicoInDTO implements Serializable {

	/**
	 * Atributo que determina el identificador único de versión para la
	 * serialización de la clase.
	 */
	private static final long serialVersionUID = 1783255568788126537L;

	private Long idPeriodoAcademico;
	@NotNull
	private Long anio;
	@NotNull
	private Long periodo;
	@NotNull
	private Date fechaInicioPeriodo;
	@NotNull
	private Date fechaFinPeriodo;
	@NotNull
	private EstadoPeriodoAcademicoEnum estado;

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