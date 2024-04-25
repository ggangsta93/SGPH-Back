/**
 * 
 */
package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

import co.edu.unicauca.sgph.common.enums.EstadoCursoHorarioEnum;

/**
 * 
 */
public class FiltroAsignaturaDTO implements Serializable {
	
	private static final long serialVersionUID = 7031117725240864688L;

	private EstadoCursoHorarioEnum estadoCursoHorario;
	
	private List<Long> listaIdFacultad;
	
	private List<Long> listaIdPrograma;

	private List<Long> listaIdAsignatura;

	private Integer semestre;

	private Integer pagina;

	private Integer registrosPorPagina;
	
	private String pensum;

	public FiltroAsignaturaDTO() {
		//Constructor sin parametros
	}

	public EstadoCursoHorarioEnum getEstadoCursoHorario() {
		return estadoCursoHorario;
	}

	public void setEstadoCursoHorario(EstadoCursoHorarioEnum estadoCursoHorario) {
		this.estadoCursoHorario = estadoCursoHorario;
	}

	public List<Long> getListaIdFacultad() {
		return listaIdFacultad;
	}

	public void setListaIdFacultad(List<Long> listaIdFacultad) {
		this.listaIdFacultad = listaIdFacultad;
	}

	public List<Long> getListaIdPrograma() {
		return listaIdPrograma;
	}

	public void setListaIdPrograma(List<Long> listaIdPrograma) {
		this.listaIdPrograma = listaIdPrograma;
	}

	public List<Long> getListaIdAsignatura() {
		return listaIdAsignatura;
	}

	public void setListaIdAsignatura(List<Long> listaIdAsignatura) {
		this.listaIdAsignatura = listaIdAsignatura;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getRegistrosPorPagina() {
		return registrosPorPagina;
	}

	public void setRegistrosPorPagina(Integer registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

}
