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

	private List<Long> listaIdAgrupadorEspacioFisico;

	private List<Long> listaIdTipoEspacioFisico;

	private String salon;

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
}