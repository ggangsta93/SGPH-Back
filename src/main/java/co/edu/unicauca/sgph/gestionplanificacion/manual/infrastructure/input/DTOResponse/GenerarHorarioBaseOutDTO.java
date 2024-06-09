package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.util.List;

public class GenerarHorarioBaseOutDTO implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = 8733120435630482471L;

	private Long idPrograma;

	private Long cantidadCursosActualizados;

	private Long cantidadCursosNoCorrelacionados;
	
	private List<String[]> lstMensajesDelProceso;

	public GenerarHorarioBaseOutDTO() {
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getCantidadCursosActualizados() {
		return cantidadCursosActualizados;
	}

	public void setCantidadCursosActualizados(Long cantidadCursosActualizados) {
		this.cantidadCursosActualizados = cantidadCursosActualizados;
	}

	public Long getCantidadCursosNoCorrelacionados() {
		return cantidadCursosNoCorrelacionados;
	}

	public void setCantidadCursosNoCorrelacionados(Long cantidadCursosNoCorrelacionados) {
		this.cantidadCursosNoCorrelacionados = cantidadCursosNoCorrelacionados;
	}

	public List<String[]> getLstMensajesDelProceso() {
		return lstMensajesDelProceso;
	}

	public void setLstMensajesDelProceso(List<String[]> lstMensajesDelProceso) {
		this.lstMensajesDelProceso = lstMensajesDelProceso;
	}
}