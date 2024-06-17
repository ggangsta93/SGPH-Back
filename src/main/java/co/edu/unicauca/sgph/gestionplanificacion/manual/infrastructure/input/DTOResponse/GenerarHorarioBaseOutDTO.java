package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.util.List;

public class GenerarHorarioBaseOutDTO implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = 8733120435630482471L;

	private Long idPrograma;

	private Long cantidadCursosHorarioCompleto;
	
	private Long cantidadCursosHorarioParcial;
	
	private Long cantidadCursosSinHorario;

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

	public Long getCantidadCursosHorarioCompleto() {
		return cantidadCursosHorarioCompleto;
	}

	public void setCantidadCursosHorarioCompleto(Long cantidadCursosHorarioCompleto) {
		this.cantidadCursosHorarioCompleto = cantidadCursosHorarioCompleto;
	}

	public Long getCantidadCursosHorarioParcial() {
		return cantidadCursosHorarioParcial;
	}

	public void setCantidadCursosHorarioParcial(Long cantidadCursosHorarioParcial) {
		this.cantidadCursosHorarioParcial = cantidadCursosHorarioParcial;
	}

	public Long getCantidadCursosSinHorario() {
		return cantidadCursosSinHorario;
	}

	public void setCantidadCursosSinHorario(Long cantidadCursosSinHorario) {
		this.cantidadCursosSinHorario = cantidadCursosSinHorario;
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