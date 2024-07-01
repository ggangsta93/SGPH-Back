package co.edu.unicauca.sgph.common.aplication.output;

public interface GestionarPersonaGatewayIntPort {

	/**
	 * Método encargado de validar si existe un email. Este es utilizado por la
	 * anotación @ExistsByEmail<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param email     Correo electrónico a validar
	 * @param idPersona Identificador único persona (Es requerido para
	 *                  actualización)
	 * @return
	 */
	public Boolean existsPersonaByEmail(String email, Long idPersona);

	/**
	 * Método encargado de validar si existe una persona con la misma
	 * identificación. Este es utilizado por la
	 * anotación @ExistsByTipoAndNumeroIdentificacion<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idTipoIdentificacion Identificador del tipo de identificación
	 * @param numeroIdentificacion Número de identificación
	 * @param idPersona            Identificador único persona (Es requerido para
	 *                             actualización)
	 * @return
	 */
	public Boolean existsPersonaByTipoAndNumeroIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion,
			Long idPersona);

}
