package co.edu.unicauca.sgph.persona.aplication.output;

import co.edu.unicauca.sgph.persona.domain.model.Persona;

public interface GestionarPersonaGatewayIntPort {

	public Persona guardarPersona(Persona persona);

	/**
	 * Método encargado de validar si existe una persona con el email consultado.
	 * Este es utilizado por la anotación @ExisteEmail<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param email     Correo electrónico a validar
	 * @param idPersona Identificador único persona (Es requerido para
	 *                  actualización)
	 * @return
	 */
	public Boolean existePersonaPorEmail(String email, Long idPersona);

	/**
	 * Método encargado de validar si existe una persona con la misma
	 * identificación. Este es utilizado por la
	 * anotación @ExistePersonaPorIdentificacion<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idTipoIdentificacion Identificador del tipo de identificación
	 * @param numeroIdentificacion Número de identificación
	 * @param idPersona            Identificador único persona (Es requerido para
	 *                             actualización)
	 * @return
	 */
	public Boolean existePersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion,
			Long idPersona);

	/**
	 * Método encargado de validar si existe una persona con el idPersona. Este es
	 * utilizado por la anotación @ExistePersonaPorIdPersona<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPersona Identificador único persona
	 * @return
	 */
	public Boolean existePersonaPorIdPersona(Long idPersona);

	/**
	 * Método encargado de consultar una persona con el tipo y número de
	 * identificación<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public Persona consultarPersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion);
}
