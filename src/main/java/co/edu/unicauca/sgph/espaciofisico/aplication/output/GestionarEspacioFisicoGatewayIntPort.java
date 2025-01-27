package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoAgrupadorDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;

public interface GestionarEspacioFisicoGatewayIntPort {

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
	 * @param lstIdEdificio
	 * @return
	 */
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<Long> lstIdEdificio);

	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos
	 * <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de los edificios
	 */
	public List<String> consultarEdificios();

	/**
	 * Método encargado de consultar todas las ubicaciones de los espacios físicos
	 * <br>
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
	 * @return Identificadores de edificios
	 */
	public List<Edificio> consultarEdificiosPorUbicacion(List<Long> lstUbicacion);

	/**
	 * Método encargado de consultar la capacidad, estado y salon de espacios
	 * físicos dado una lista de identificadores únicos <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdEspacioFisico
	 * @return
	 */
	public List<EspacioFisico> consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(List<Long> lstIdEspacioFisico);
	
	/**
	 * Método encargado de obtener el espacio físico principal de un curso dado el
	 * identificador único de horario <br>
	 * 
	 * @author apedro
	 * 
	 * @param idHorario
	 * @param esPrincipal
	 * @return
	 */
	public EspacioFisico consultarEspacioFisicoCursoPorIdHorario(Long idHorario, Boolean esPrincipal);	

	List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(Long idAgrupador);

	List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(Long idAgrupador);

	List<EspacioFisicoDTO> consultarEspaciosFisicosConFiltro(FiltroEspacioFisicoAgrupadorDTO filtro);

	List<RecursoOutDTO> obtenerListaRecursos();
	EspacioFisico guardarEspacioFisico(EspacioFisicoInDTO espacioFisicoInDTO);

	void activarInactivarEspacioFisico(Long id);
	
	public Boolean existsEspacioFisicoByOid(String OID);
	
	public Boolean existsEspacioFisicoByNombre(String salon);
	
	MensajeOutDTO cargaMasivaEspaciosFisicos(EspacioFisicoInDTO espacioFisico);
	
	List<RecursoOutDTO> obtenerRecursosPorEspacioFisico(Long idEspacioFisico);
	
	Map<String, Object> procesarArchivoExcel(MultipartFile file) throws Exception;
}
