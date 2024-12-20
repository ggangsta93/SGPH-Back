package co.edu.unicauca.sgph.persona.aplication.output;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;

public interface GestionarPersonaGatewayIntPort {

	public Persona guardarPersona(Persona persona);

	/**
	 * Método encargado de validar si existe una persona con el email consultado.
	 * Este es utilizado por la anotación @ExisteEmail<br>
	 * 
	 * @author apedro
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
	 * @author apedro
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
	 * @author apedro
	 * 
	 * @param idPersona Identificador único persona
	 * @return
	 */
	public Boolean existePersonaPorIdPersona(Long idPersona);

	/**
	 * Método encargado de consultar una persona con el tipo y número de
	 * identificación<br>
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

    Persona consultarPersonaPorEmail(String email);
    
    Page<Persona> consultarPersonasPaginadas(Pageable pageable);
    
    Persona obtenerPersonaPorId(Long idPersona);
    
    void eliminarPersona(Long idPersona);

    Optional<Persona> findById(Long idPersona);
}
