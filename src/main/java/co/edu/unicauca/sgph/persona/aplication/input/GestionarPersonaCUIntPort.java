package co.edu.unicauca.sgph.persona.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;

public interface GestionarPersonaCUIntPort {

	public Persona guardarPersona(Persona persona);
	
	/**
	 * Método encargado de consultar una persona con el tipo y número de identificación<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public Persona consultarPersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion);

	/**
	 * Método encargado de consultar todos los tipos de identificación de
	 * persona<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<TipoIdentificacion> consultarTiposIdentificacion();
	/**
	 * Método encargado de consultar la persona por email
	 * persona<br>
	 *
	 * @author apedro
	 *
	 * @return
	 */
    Persona consultarPersonaPorEmail(String email);
}