package co.edu.unicauca.sgph.docente.domain.model;

import java.util.List;

import co.edu.unicauca.sgph.common.domain.model.Persona;
import co.edu.unicauca.sgph.curso.domain.model.Curso;

public class Docente extends Persona {

	private String codigo;
	
	private Boolean estado;
	
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}
