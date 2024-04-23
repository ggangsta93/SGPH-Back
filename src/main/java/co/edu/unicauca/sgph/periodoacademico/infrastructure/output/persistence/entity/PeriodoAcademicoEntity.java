package co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERIODO_ACADEMICO")
public class PeriodoAcademicoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERIODO_ACADEMICO", nullable = false)
	private Long idPeriodoAcademico;
	@Column(name = "ANIO", nullable = false)
	private Long anio;
	@Column(name = "PERIODO", nullable = false)
	private Long periodo;
	@Column(name = "FECHA_INICIO_PERIODO", nullable = false)
	private Date fechaInicioPeriodo;
	@Column(name = "FECHA_FIN_PERIODO", nullable = false)
	private Date fechaFinPeriodo;
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO", nullable = false)
	private EstadoPeriodoAcademicoEnum estado;
	
	public PeriodoAcademicoEntity() {
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