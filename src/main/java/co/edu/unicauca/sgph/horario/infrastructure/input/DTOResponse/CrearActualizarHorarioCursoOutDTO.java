package co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse;

import java.util.List;

public class CrearActualizarHorarioCursoOutDTO {
	
	private Boolean esExitoso;

	private List<String> lstMensajesSolapamientos;

	public Boolean getEsExitoso() {
		return esExitoso;
	}

	public void setEsExitoso(Boolean esExitoso) {
		this.esExitoso = esExitoso;
	}

	public List<String> getLstMensajesSolapamientos() {
		return lstMensajesSolapamientos;
	}

	public void setLstMensajesSolapamientos(List<String> lstMensajesSolapamientos) {
		this.lstMensajesSolapamientos = lstMensajesSolapamientos;
	}
}