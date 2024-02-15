package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.DocenteEntity;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

@Entity
@Table(name = "CURSO")
public class CursoEntity {

	@Id
	@SequenceGenerator(name = "SEC_CURSO_GENERATOR", sequenceName = "SEC_CURSO", allocationSize = 300, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_CURSO_GENERATOR")
	@Column(name = "ID_CURSO", nullable = false)
	private Long idCurso;

	@Column(name = "GRUPO")
	private String grupo;

	@Column(name = "CUPO")
	private Integer cupo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ASIGNATURA")
	private AsignaturaEntity asignatura;

	@ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY)
	private List<DocenteEntity> docentes;

	@OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
	private List<HorarioEntity> horarios;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PERIODO_ACADEMICO")
	private PeriodoAcademicoEntity periodoAcademico;

	public CursoEntity() {
	}

	public CursoEntity(String grupo, AsignaturaEntity asignatura) {
		this.grupo = grupo;
		this.asignatura = asignatura;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public AsignaturaEntity getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(AsignaturaEntity asignatura) {
		this.asignatura = asignatura;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public List<DocenteEntity> getDocentes() {
		return docentes;
	}

	public void setDocentes(List<DocenteEntity> docentes) {
		this.docentes = docentes;
	}

	public Integer getCupo() {
		return cupo;
	}

	public void setCupo(Integer cupo) {
		this.cupo = cupo;
	}

	public PeriodoAcademicoEntity getPeriodoAcademico() {
		return periodoAcademico;
	}

	public void setPeriodoAcademico(PeriodoAcademicoEntity periodoAcademico) {
		this.periodoAcademico = periodoAcademico;
	}
}