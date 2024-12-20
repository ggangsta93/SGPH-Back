package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoAgrupadorDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroFranjaLibreDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;

public interface GestionarEspacioFisicoCUIntPort {

	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico);

	/**
	 * Método encargado de obtener los horarios formateados a partir del curso<br>
	 * Ejemplo del formato: 'LUNES 07:00-09:00 Salón 204-Edificio nuevo'	 
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param esPrincipal
	 * @return
	 */
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso, Boolean esPrincipal);

	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author apedro
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico);
	
	/**
	 * Método encargado de consultar los espacios físicos por diferentes criterios
	 * de busqueda y retornarlos de manera paginada<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroEspacioFisicoDTO DTO con los filtros de busqueda
	 * @return
	 */
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(@RequestBody FiltroEspacioFisicoDTO filtroEspacioFisicoDTO);

	/**
	 * Método encargado de consultar los tipo de espacios físicos asociados a una
	 * lista de edificios <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstEdificio
	 * @return
	 */
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<Long> lstIdEdificio);
	
	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de los edificios
	 */
	public List<String> consultarEdificios();
	
	/**
	 * Método encargado de consultar todas las ubicaciones de los espacios físicos <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de las ubicaciones
	 */
	public List<String> consultarUbicaciones();
	
	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 *
	 * @author apedro
	 *
	 * @return Identificadores de los edificios
	 */
	public List<Edificio> consultarEdificiosPorUbicacion(List<Long> lstIdUbicacion);

	List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(Long idAgrupador);

	List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(Long idAgrupador);

	List<EspacioFisicoDTO> consultarEspaciosFisicosConFiltro(FiltroEspacioFisicoAgrupadorDTO filtro);

	List<RecursoOutDTO> obtenerListaRecursos();

	EspacioFisico guardarEspacioFisico(EspacioFisicoInDTO espacioFisicoInDTO);

	void activarInactivarEspacioFisico(Long id);
	
	MensajeOutDTO cargaMasivaEspacioFisico(EspacioFisicoInDTO espacioFisico);
	
	List<EspacioFisicoDTO> consultarFranjasLibres(FiltroFranjaLibreDTO filtroFranjaLibreDTO);

}