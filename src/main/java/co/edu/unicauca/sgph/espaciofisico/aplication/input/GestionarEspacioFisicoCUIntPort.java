package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;

public interface GestionarEspacioFisicoCUIntPort {



	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico);

	/**
	 * Método encargado de obtener los horarios formateados a partir del curso<br>
	 * Ejemplo del formato: 'LUNES 07:00-09:00 Salón 204-Edificio nuevo'	 
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso);

	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico);
	
	/**
	 * Método encargado de consultar los espacios físicos por diferentes criterios
	 * de busqueda y retornarlos de manera paginada<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param filtroEspacioFisicoDTO DTO con los filtros de busqueda
	 * @return
	 */
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(@RequestBody FiltroEspacioFisicoDTO filtroEspacioFisicoDTO);

	/**
	 * Método encargado de consultar los tipo de espacios físicos asociados a una
	 * lista de edificios <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstEdificio
	 * @return
	 */
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<String> lstEdificio);
	
	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	public List<String> consultarEdificios();
	
	/**
	 * Método encargado de consultar todas las ubicaciones de los espacios físicos <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de las ubicaciones
	 */
	public List<String> consultarUbicaciones();
	
	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	public List<String> consultarEdificiosPorUbicacion(List<String> lstUbicacion);

	List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(Long idAgrupador);

	List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(Long idAgrupador);
}