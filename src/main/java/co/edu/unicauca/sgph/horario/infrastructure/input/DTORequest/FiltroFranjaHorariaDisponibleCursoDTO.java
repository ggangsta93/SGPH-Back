package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FiltroFranjaHorariaDisponibleCursoDTO implements Serializable {

	private static final long serialVersionUID = -1067303712899236108L;

	private Long idCurso;

	private Long idAsignatura;

	private String horaInicio;

	private String horaFin;

	private Long duracion;

	private List<Long> listaIdUbicacion;

	private List<DiaSemanaEnum> listaDiaSemanaEnum;

	private List<Long> listaIdAgrupadorEspacioFisico;

	private List<Long> listaIdTipoEspacioFisico;

	private String salon;
	
	/*
	 * Indicador que define si el horario a consultar es para horario principal o
	 * secundario
	 */
	private Boolean esPrincipal;

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

	public List<Long> getListaIdTipoEspacioFisico() {
		return listaIdTipoEspacioFisico;
	}

	public void setListaIdTipoEspacioFisico(List<Long> listaIdTipoEspacioFisico) {
		this.listaIdTipoEspacioFisico = listaIdTipoEspacioFisico;
	}

	public List<Long> getListaIdAgrupadorEspacioFisico() {
		return listaIdAgrupadorEspacioFisico;
	}

	public void setListaIdAgrupadorEspacioFisico(List<Long> listaIdAgrupadorEspacioFisico) {
		this.listaIdAgrupadorEspacioFisico = listaIdAgrupadorEspacioFisico;
	}

	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	public List<Long> getListaIdUbicacion() {
		return listaIdUbicacion;
	}

	public void setListaIdUbicacion(List<Long> listaIdUbicacion) {
		this.listaIdUbicacion = listaIdUbicacion;
	}

	public Long getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
}