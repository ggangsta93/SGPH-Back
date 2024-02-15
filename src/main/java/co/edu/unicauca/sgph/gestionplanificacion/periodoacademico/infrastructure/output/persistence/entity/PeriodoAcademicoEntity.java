package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERIODO_ACADEMICO")
public class PeriodoAcademicoEntity {

	@Id
	@SequenceGenerator(name = "SEC_PERIODO_ACADEMICO_GENERATOR", sequenceName = "SEC_PERIODO_ACADEMICO", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PERIODO_ACADEMICO_GENERATOR")
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
	@Column(name = "ESTADO", nullable = false)
	private Boolean estado;
	
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}