package co.edu.unicauca.sgph.agrupador.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

public interface GestionarAgrupadorEspacioFisicoCUIntPort {

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idFacultad
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados a
	 * un curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(Long idCurso);

	Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(FiltroGrupoDTO filtro);

	AgrupadorEspacioFisico guardarGrupo(AgrupadorEspacioFisico agrupador);
    
    MensajeOutDTO guardarAsignacion(AsignacionEspacioFisicoDTO asignacion);
}
