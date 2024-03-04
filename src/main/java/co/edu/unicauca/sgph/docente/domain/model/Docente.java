package co.edu.unicauca.sgph.docente.domain.model;

import java.util.List;

import co.edu.unicauca.sgph.common.domain.model.Persona;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;

public class Docente extends Persona {

	private String codigo;
	
	private EstadoDocenteEnum estado;
	
	private List<Curso> cursos;	
	
	public Docente() {
		//Constructor sin parámetros
	}
	
	public Docente(Long idPersona) {
		super(idPersona);
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public EstadoDocenteEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoDocenteEnum estado) {
		this.estado = estado;
	}
}