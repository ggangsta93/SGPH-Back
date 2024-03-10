package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AulaEntity;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;

@Entity
@Table(name = "HORARIO")
public class HorarioEntity {

	@Id
	@SequenceGenerator(name = "SEC_HORARIO_GENERATOR", sequenceName = "SEC_HORARIO", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_HORARIO_GENERATOR")
	@Column(name = "ID_HORARIO", nullable = false)
	private Long idHorario;

	@Column(name = "DIA")
	@Enumerated(EnumType.STRING)
	private DiaSemanaEnum dia;

	@Column(name = "HORA_INICIO", columnDefinition = "TIME")
	private LocalTime horaInicio;

	@Column(name = "HORA_FIN", columnDefinition = "TIME")
	private LocalTime horaFin;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CURSO")
	private CursoEntity curso;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "HORARIO_AULA", joinColumns = @JoinColumn(name = "ID_HORARIO"), inverseJoinColumns = @JoinColumn(name = "ID_AULA"))
	private List<AulaEntity> aulas;

	public HorarioEntity() {
		aulas= new ArrayList<>();
	}

	public HorarioEntity(DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin, CursoEntity curso, List<AulaEntity> aulas) {
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.curso = curso;
		this.aulas = aulas;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}

	public DiaSemanaEnum getDia() {
		return dia;
	}

	public void setDia(DiaSemanaEnum dia) {
		this.dia = dia;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}

	public CursoEntity getCurso() {
		return curso;
	}

	public void setCurso(CursoEntity curso) {
		this.curso = curso;
	}

	public List<AulaEntity> getAulas() {
		return aulas;
	}

	public void setAulas(List<AulaEntity> aulas) {
		this.aulas = aulas;
	}
}
