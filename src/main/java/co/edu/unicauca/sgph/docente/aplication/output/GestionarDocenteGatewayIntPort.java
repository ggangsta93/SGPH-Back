package co.edu.unicauca.sgph.docente.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

public interface GestionarDocenteGatewayIntPort {

	public Docente guardarDocente(Docente docente);

	/**
	 * Método encargado de consultar un docente por su identificación<br>
	 * 
	 * @author apedro
	 * 
	 * @param idTipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public Docente consultarDocentePorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion);

	/**
	 * Método encargado de consultar un docente por su identificador único <br>
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @return
	 */
	public Docente consultarDocentePorIdPersona(Long idPersona);

	/**
	 * Método encargado de consultar los nombres de docentes por curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<String> consultarNombresDocentesPorIdCurso(Long idCurso);

	/**
	 * Método encargado de obtener los docentes dado un conjunto de criterios de
	 * busqueda.<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroCursoDTO
	 * @return
	 */
	public Page<DocenteOutDTO> consultarDocentes(FiltroDocenteDTO filtroDocenteDTO);

	/**
	 * Método encargado de obtener los docentes asociadas a un curso.<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<Docente> consultarDocentePorIdCurso(Long idCurso);

	/**
	 * Método encargado de validar si existe un docente por su código. Este es
	 * utilizado por la anotación @existsByCodigo<br>
	 * 
	 * @author apedro
	 * 
	 * @param codigo    Código del docente
	 * @param idDocente Identificador único docente (Es requerido para
	 *                  actualización)
	 * @return
	 */
	public Boolean existsDocenteByCodigo(String codigo, Long idDocente);


	/**
	 * Método encargado de validar si ya existe un docente con el idPersona. Este es
	 * utilizado por la anotación @existeIdPersona<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPersona Identificador único persona
	 * @param idDocente Identificador único docente (Es requerido para
	 *                  actualización)
	 * @return
	 */
	public Boolean existeDocentePorIdPersona(Long idPersona, Long idDocente);
}
