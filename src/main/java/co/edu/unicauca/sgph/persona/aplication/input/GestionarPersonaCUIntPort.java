package co.edu.unicauca.sgph.persona.aplication.input;

import co.edu.unicauca.sgph.persona.domain.model.Persona;

public interface GestionarPersonaCUIntPort {

	public Persona guardarPersona(Persona persona);
	
	/**
	 * Método encargado de consultar una persona con el tipo y número de identificación<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public Persona consultarPersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion);

}