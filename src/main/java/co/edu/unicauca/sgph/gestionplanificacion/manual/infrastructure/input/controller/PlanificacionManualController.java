package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.common.enums.EstadoCursoHorarioEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.GenerarHorarioBaseInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.GenerarHorarioBaseOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.mapper.PlanificacionManualRestMapper;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarDocentesCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarDocentesCursoOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarHorarioCursoOutDTO;

@RestController
@RequestMapping("/PlanificacionManual")
public class PlanificacionManualController extends CommonEJB{

	private GestionarPlanificacionManualCUIntPort gestionarPlanificacionManualCUIntPort;
	private PlanificacionManualRestMapper planificacionManualRestMapper;

	public PlanificacionManualController(GestionarPlanificacionManualCUIntPort gestionarPlanificacionManualCUIntPort,
			PlanificacionManualRestMapper planificacionManualRestMapper) {
		this.gestionarPlanificacionManualCUIntPort = gestionarPlanificacionManualCUIntPort;
		this.planificacionManualRestMapper = planificacionManualRestMapper;
	}

	/*************************************************
	 * Planificación manual
	 *************************************************/
	
	/**
	 * Método encargado de consultar los cursos por diferentes criterios de busqueda
	 * y retornarlos de manera paginada
	 * 
	 * @author apedro
	 * 
	 * @param filtroCursoPlanificacionDTO DTO con los filtros de busqueda
	 * @return
	 */
	@GetMapping("/consultarCursosPlanificacionPorFiltro")
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
		    @RequestParam(required = false) EstadoCursoHorarioEnum estadoCursoHorario,
		    @RequestParam(required = false) List<Long> listaIdFacultad,
		    @RequestParam(required = false) List<Long> listaIdPrograma,
		    @RequestParam(required = false) List<Long> listaIdAsignatura,
		    @RequestParam(required = false) Integer semestre,
		    @RequestParam(required = true) Integer pagina,
		    @RequestParam(required = true) Integer registrosPorPagina,
		    @RequestParam(required = false) Integer cantidadDocentes) {

		    FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO = new FiltroCursoPlanificacionDTO();
		    filtroCursoPlanificacionDTO.setEstadoCursoHorario(estadoCursoHorario);
		    filtroCursoPlanificacionDTO.setListaIdFacultad(listaIdFacultad);
		    filtroCursoPlanificacionDTO.setListaIdPrograma(listaIdPrograma);
		    filtroCursoPlanificacionDTO.setListaIdAsignatura(listaIdAsignatura);
		    filtroCursoPlanificacionDTO.setSemestre(semestre);
		    filtroCursoPlanificacionDTO.setPagina(pagina);
		    filtroCursoPlanificacionDTO.setRegistrosPorPagina(registrosPorPagina);
		    filtroCursoPlanificacionDTO.setCantidadDocentes(cantidadDocentes);

		    return this.gestionarPlanificacionManualCUIntPort.consultarCursosPlanificacionPorFiltro(filtroCursoPlanificacionDTO);
	}	

	/**
	 * Método encargado de consultar la información gneral de los cursos de un
	 * programa dado el identificador del programa
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma Identificador del programa
	 * @return
	 */
	@GetMapping("/consultarInfoGeneralCursosPorPrograma")
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(@RequestParam Long idPrograma) {
		return this.gestionarPlanificacionManualCUIntPort.consultarInfoGeneralCursosPorPrograma(idPrograma);
	}

	/**
	 * Método encargado de crear y/o actualizar los horarios de un curso.
	 * 
	 * @author apedro
	 * 
	 * @param crearActualizarHorarioCursoInDTO
	 * @return
	 */
	@PostMapping("/crearActualizarHorarioCursoDTO")
	public ResponseEntity<CrearActualizarHorarioCursoOutDTO> crearActualizarHorarioCursoDTO(
			@RequestBody CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoOutDTO = this.gestionarPlanificacionManualCUIntPort
				.crearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO);
		if (crearActualizarHorarioCursoOutDTO.getEsExitoso().equals(Boolean.FALSE)) {
			return ResponseEntity.badRequest().body(crearActualizarHorarioCursoOutDTO);
		}
		return ResponseEntity.ok(crearActualizarHorarioCursoOutDTO);
	}

	/**
	 * Método encargado de crear y/o actualizar los horarios secundarios de un
	 * curso.
	 * 
	 * @author apedro
	 * 
	 * @param crearActualizarHorarioCursoInDTO
	 * @return
	 */
	@PostMapping("/crearActualizarHorarioSecundarioCurso")
	public ResponseEntity<CrearActualizarHorarioCursoOutDTO> crearActualizarHorarioSecundarioCurso(
			@RequestBody CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoOutDTO = this.gestionarPlanificacionManualCUIntPort
				.crearActualizarHorarioSecundarioCurso(crearActualizarHorarioCursoInDTO);
		if (crearActualizarHorarioCursoOutDTO.getEsExitoso().equals(Boolean.FALSE)) {
			return ResponseEntity.badRequest().body(crearActualizarHorarioCursoOutDTO);
		}
		return ResponseEntity.ok(crearActualizarHorarioCursoOutDTO);
	}

	/**
	 * Método encargado de crear y/o actualizar los docentes de un curso.
	 * 
	 * @author apedro
	 * 
	 * @param crearActualizarDocentesCursoInDTO
	 * @return
	 */
	@PostMapping("/crearActualizarDocentesCursoDTO")
	public CrearActualizarDocentesCursoOutDTO crearActualizarDocentesCursoDTO(
			@RequestBody CrearActualizarDocentesCursoInDTO crearActualizarDocentesCursoInDTO) {
		return this.gestionarPlanificacionManualCUIntPort
				.crearActualizarDocentesCursoDTO(crearActualizarDocentesCursoInDTO);
	}

	/**
	 * Método encargado de obtener las franjas disponibles de un curso dado un
	 * conjunto de criterios de busqueda; este método considera los horarios de los
	 * docentes y espacios físicos.
	 * 
	 * @author apedro
	 * 
	 * @param filtroFranjaHorariaDisponibleCursoDTO
	 * @return
	 */	
	@GetMapping("/consultarFranjasHorariasDisponiblesPorCurso")
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
	        @RequestParam(required = false) Long idCurso,
	        @RequestParam(required = false) Long idAsignatura,
	        @RequestParam(required = false) String horaInicio,
	        @RequestParam(required = false) String horaFin,
	        @RequestParam(required = false) Long duracion,
	        @RequestParam(required = false) List<Long> listaIdUbicacion,
	        @RequestParam(required = false) List<DiaSemanaEnum> listaDiaSemanaEnum,
	        @RequestParam(required = false) List<Long> listaIdAgrupadorEspacioFisico,
	        @RequestParam(required = false) List<Long> listaIdTipoEspacioFisico,
	        @RequestParam(required = false) String salon,
	        @RequestParam(required = false) Boolean esPrincipal) {

	    FiltroFranjaHorariaDisponibleCursoDTO filtro = new FiltroFranjaHorariaDisponibleCursoDTO();
	    filtro.setIdCurso(idCurso);
	    filtro.setIdAsignatura(idAsignatura);
	    filtro.setHoraInicio(horaInicio);
	    filtro.setHoraFin(horaFin);
	    filtro.setDuracion(duracion);
	    filtro.setListaIdUbicacion(listaIdUbicacion);
	    filtro.setListaDiaSemanaEnum(listaDiaSemanaEnum);
	    filtro.setListaIdAgrupadorEspacioFisico(listaIdAgrupadorEspacioFisico);
	    filtro.setListaIdTipoEspacioFisico(listaIdTipoEspacioFisico);
	    filtro.setSalon(salon);
	    filtro.setEsPrincipal(esPrincipal);

	    return this.gestionarPlanificacionManualCUIntPort.consultarFranjasHorariasDisponiblesPorCurso(filtro);
	}	

	/**
	 * Método encargado de obtener las franjas horarias de un curso dado el
	 * identificador del curso
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param esPrincipal
	 * @return
	 */
	@GetMapping("/consultarFranjasHorariaCursoPorIdCurso")
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(@RequestParam Long idCurso,
			@RequestParam Boolean esPrincipal) {
		return this.gestionarPlanificacionManualCUIntPort.consultarFranjasHorariaCursoPorIdCurso(idCurso, esPrincipal);
	}

	/**
	 * Método encargado de obtener los nombres completos de cada espacio físico.
	 * Necesario para la funcionalidad de asociar espacios físicos (Asignar los
	 * horarios al curso) de la pantalla Planificación Manual. Este servicio es útil
	 * para mapear los nombres de los espacios físicos mediante su identificador
	 * único. 
	 * 
	 * Nota: Actualmente como se encuentra los datos de espacios físicos sólo se
	 * está obteniendo el identificador único del espacio físico y el salon (Incluye
	 * el formato que se necesario, ya no es necesrio armarlo)
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarFormatoPresentacionFranjaHorariaCurso")
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.gestionarPlanificacionManualCUIntPort.consultarFormatoPresentacionFranjaHorariaCurso();
	}
	
	
	/*************************************************
	 * Generación basada en semestre anterior por programa
	 *************************************************/
	
	/**
	 * Método encargado de generar un horario base para un programa partiendo del
	 * horario del semestre anterior del mismo</br>
	 * 
	 * @author apedro
	 * 
	 * @param generarHorarioBaseInDTO Información requerida para generar el horario
	 *                                base
	 * @return
	 */
	@PostMapping("/generarHorarioBasadoEnSemestreAnteriorPorPrograma")
	public GenerarHorarioBaseOutDTO generarHorarioBasadoEnSemestreAnteriorPorPrograma(
			@RequestBody GenerarHorarioBaseInDTO generarHorarioBaseInDTO) {
		return this.gestionarPlanificacionManualCUIntPort
				.generarHorarioBasadoEnSemestreAnteriorPorPrograma(generarHorarioBaseInDTO);
	}
	
	// Método temporal
	@GetMapping("/simularCargueLabor")
	public void simularCargueLabor(@RequestParam Long idPrograma) {
		this.gestionarPlanificacionManualCUIntPort.simularCargueLabor(idPrograma);
	}
	

	/*************************************************
	 * Eliminar horario por programa
	 *************************************************/
	
	/**
	 * Método encargado de eliminar todo el horario de un programa</br>
	 * 
	 * @author apedro
	 * 
	 * @param eliminarHorarioDTO Información necesaria para eliminar el horario de
	 *                           un programa
	 * @return Booleano que indica si se eliminó con exito el horario
	 */	
	@DeleteMapping("/eliminarHorarioPrograma")
	public Boolean eliminarHorarioPrograma(@RequestParam Long idPrograma) {
	    EliminarHorarioInDTO eliminarHorarioDTO = new EliminarHorarioInDTO();
	    eliminarHorarioDTO.setIdPrograma(idPrograma);
	    return this.gestionarPlanificacionManualCUIntPort.eliminarHorarioPrograma(eliminarHorarioDTO);
	}
	
	/*************************************************
	 * Consultar horarios de Espacio físico y Docente
	 *************************************************/

	/**
	 * Método encargado de obtener todas las franjas horarias de un docente
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @return
	 */
	@GetMapping("/consultarFranjasDocentePorIdPersona")
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(@RequestParam Long idPersona) {
		return this.gestionarPlanificacionManualCUIntPort.consultarFranjasDocentePorIdPersona(idPersona);
	}

	/**
	 * Método encargado de obtener todas las franjas horarias de un espacio físico
	 *
	 * @author apedro
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	@GetMapping("/consultarFranjasEspacioFisicoPorIdEspacioFisico")
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(
			@RequestParam Long idEspacioFisico) {
		return this.gestionarPlanificacionManualCUIntPort
				.consultarFranjasEspacioFisicoPorIdEspacioFisico(idEspacioFisico);
	}
	
	

}