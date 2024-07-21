package co.edu.unicauca.sgph.agrupador.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

public interface GestionarAgrupadorEspacioFisicoGatewayIntPort {

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos<br>
	 * 
	 * @author apedro
	 * 
	 * @param idAgrupadorEspacioFisico
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			List<Long> idAgrupadorEspacioFisico);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos de facultades<br>
	 * 
	 * @author apedro
	 * 
	 * @param idFacultad
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados a
	 * un curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(Long idCurso);

	Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(FiltroGrupoDTO filtro);

	AgrupadorEspacioFisico guardarGrupo(AgrupadorEspacioFisico agrupadorEspacioFisicor);
	
	MensajeOutDTO guardarAsignacion(AsignacionEspacioFisicoDTO asignacion);
	
	/**
	 * Método encargado de validar si existe un nombre de agrupador. Este es
	 * utilizado por la anotación @existeNombreAgrupador<br>
	 * 
	 * @author apedro
	 * 
	 * @param nombreAgrupador          Nombre de agrupador
	 * @param idAgrupadorEspacioFisico Identificador único agrupador (Es requerido
	 *                                 para actualización)
	 * @return
	 */
	public Boolean existeAgrupadorPorNombreAgrupador(String nombreAgrupador, Long idAgrupadorEspacioFisico);

}
