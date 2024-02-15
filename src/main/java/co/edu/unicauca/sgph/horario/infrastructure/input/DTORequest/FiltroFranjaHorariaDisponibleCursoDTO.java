package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FiltroFranjaHorariaDisponibleCursoDTO implements Serializable {

	private static final long serialVersionUID = -1067303712899236108L;

	private Long idCurso;
	
	private String horaInicio;
	
	private String horaFin;
	
	private Long duracion;
	
	private List<DiaSemanaEnum> listaDiaSemanaEnum;
	
	private List<Long> listaIdFacultad;
	
	private List<Long> listaIdEdificio;
	
	private List<Long> listaIdTipoAula;
	
	private List<String> listaNumeroAula;

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Long getDuracion() {
		return duracion;
	}

	public void setDuracion(Long duracion) {
		this.duracion = duracion;
	}

	public List<DiaSemanaEnum> getListaDiaSemanaEnum() {
		return listaDiaSemanaEnum;
	}

	public void setListaDiaSemanaEnum(List<DiaSemanaEnum> listaDiaSemanaEnum) {
		this.listaDiaSemanaEnum = listaDiaSemanaEnum;
	}

	public List<Long> getListaIdFacultad() {
		return listaIdFacultad;
	}

	public void setListaIdFacultad(List<Long> listaIdFacultad) {
		this.listaIdFacultad = listaIdFacultad;
	}

	public List<Long> getListaIdEdificio() {
		return listaIdEdificio;
	}

	public void setListaIdEdificio(List<Long> listaIdEdificio) {
		this.listaIdEdificio = listaIdEdificio;
	}

	public List<Long> getListaIdTipoAula() {
		return listaIdTipoAula;
	}

	public void setListaIdTipoAula(List<Long> listaIdTipoAula) {
		this.listaIdTipoAula = listaIdTipoAula;
	}

	public List<String> getListaNumeroAula() {
		return listaNumeroAula;
	}

	public void setListaNumeroAula(List<String> listaNumeroAula) {
		this.listaNumeroAula = listaNumeroAula;
	}
}