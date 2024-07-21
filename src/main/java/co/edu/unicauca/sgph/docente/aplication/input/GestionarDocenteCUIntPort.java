package co.edu.unicauca.sgph.docente.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

public interface GestionarDocenteCUIntPort {

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
	 * @param filtroDocenteDTO
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

}