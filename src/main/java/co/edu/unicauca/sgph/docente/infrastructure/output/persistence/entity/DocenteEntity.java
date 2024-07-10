package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity.DepartamentoEntity;
import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity.PersonaEntity;

@Entity
@Table(name = "DOCENTE", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"CODIGO"}),
	    @UniqueConstraint(columnNames = {"ID_PERSONA"})
})
public class DocenteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DOCENTE", nullable = false)
	private Long idDocente;
	
	@Column(name = "CODIGO")
	private String codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO")
	private EstadoDocenteEnum estado;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "DOCENTE_CURSO",
			joinColumns = @JoinColumn(name = "ID_DOCENTE"),
			inverseJoinColumns = @JoinColumn(name = "ID_CURSO")
	)
	private List<CursoEntity> cursos;
	
	@ManyToOne
	@JoinColumn(name = "ID_DEPARTAMENTO", nullable = true)
	private DepartamentoEntity departamento;
	
	@OneToOne
    @JoinColumn(name = "ID_PERSONA", nullable = false)
    private PersonaEntity persona;

	public DocenteEntity() {
		this.cursos = new ArrayList<>();
	}

	public Long getIdDocente() {
		return idDocente;
	}

	public void setIdDocente(Long idDocente) {
		this.idDocente = idDocente;
	}
	
	public List<CursoEntity> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoEntity> cursos) {
		this.cursos = cursos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public EstadoDocenteEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoDocenteEnum estado) {
		this.estado = estado;
	}

	public DepartamentoEntity getDepartamento() {
		return departamento;
	}

	public void setDepartamento(DepartamentoEntity departamento) {
		this.departamento = departamento;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}
}