package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.DepartamentoEntity;

@Entity
@Table(name = "DOCENTE")
@PrimaryKeyJoinColumn(name = "ID_PERSONA")
public class DocenteEntity extends PersonaEntity {

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
	@JoinColumn(name = "ID_DEPARTAMENTO")
	private DepartamentoEntity departamento;

	public DocenteEntity() {
		this.cursos = new ArrayList<>();
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
}