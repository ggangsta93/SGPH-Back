package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest;

import java.io.Serializable;

public class EliminarHorarioInDTO implements Serializable {

	/**
	 * Atributo que determina 
	 */
	private static final long serialVersionUID = -1974654623058382618L;

	private Long idPrograma;	
		
	public EliminarHorarioInDTO() {
	}
	
	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}
}