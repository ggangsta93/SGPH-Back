package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;

@Entity
@Table(name = "DOCENTE")
@PrimaryKeyJoinColumn(name = "ID_PERSONA")
public class DocenteEntity extends PersonaEntity {

	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "ESTADO")
	private Boolean estado;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "DOCENTE_CURSO", joinColumns = @JoinColumn(name = "ID_DOCENTE"), inverseJoinColumns = @JoinColumn(name = "ID_CURSO"))
	private List<CursoEntity> cursos;

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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
