package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/PlanificacionManual")
public class PlanificacionManualController {

	private GestionarPlanificacionManualCUIntPort gestionarPlanificacionManualCUIntPort;
	private PlanificacionManualRestMapper planificacionManualRestMapper;

	public PlanificacionManualController(GestionarPlanificacionManualCUIntPort gestionarPlanificacionManualCUIntPort,
			PlanificacionManualRestMapper planificacionManualRestMapper) {
		this.gestionarPlanificacionManualCUIntPort = gestionarPlanificacionManualCUIntPort;
		this.planificacionManualRestMapper = planificacionManualRestMapper;
	}

	/*
	 * @Deprecated
	 * 
	 * @GetMapping("/consultarCruceHorarioDocente") public List<Object[]>
	 * consultarCruceHorarioDocente(@RequestParam Long idCurso, @RequestParam
	 * DiaSemanaEnum dia,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaInicio,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaFin) { return
	 * this.gestionarHorarioCUIntPort.consultarCruceHorarioDocente(idCurso, dia,
	 * horaInicio, horaFin); }
	 * 
	 * @Deprecated
	 * 
	 * @GetMapping("/consultarCruceHorarioAula") public List<HorarioOutDTO>
	 * consultarCruceHorarioAula(@RequestParam List<Long> lstIdAula,
	 * 
	 * @RequestParam DiaSemanaEnum dia, @RequestParam @DateTimeFormat(pattern =
	 * "HH:mm") LocalTime horaInicio,
	 * 
	 * @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime horaFin) { return
	 * this.horarioRestMapper.toLstHorarioOutDTO(this.gestionarHorarioCUIntPort
	 * .consultarCruceHorarioAula(lstIdAula, dia, horaInicio, horaFin)); }
	 */

	/*************************************************
	 * Planificación manual
	 *************************************************/
	
	/**
	 * Método encargado de consultar los cursos por diferentes criterios de busqueda
	 * y retornarlos de manera paginada
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param filtroCursoPlanificacionDTO DTO con los filtros de busqueda
	 * @return
	 */
	@PostMapping("/consultarCursosPlanificacionPorFiltro")
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			@RequestBody FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO) {
		return this.gestionarPlanificacionManualCUIntPort
				.consultarCursosPlanificacionPorFiltro(filtroCursoPlanificacionDTO);
	}

	/**
	 * Método encargado de consultar la información gneral de los cursos de un
	 * programa dado el identificador del programa
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param crearActualizarHorarioCursoInDTO
	 * @return
	 */
	@PostMapping("/crearActualizarHorarioCursoDTO")
	public CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoDTO(
			@RequestBody CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		return this.gestionarPlanificacionManualCUIntPort.crearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO);
	}

	/**
	 * Método encargado de crear y/o actualizar los horarios secundarios de un
	 * curso.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param crearActualizarHorarioCursoInDTO
	 * @return
	 */
	@PostMapping("/crearActualizarHorarioSecundarioCurso")
	public CrearActualizarHorarioCursoOutDTO crearActualizarHorarioSecundarioCurso(
			@RequestBody CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		return this.gestionarPlanificacionManualCUIntPort
				.crearActualizarHorarioSecundarioCurso(crearActualizarHorarioCursoInDTO);
	}

	/**
	 * Método encargado de crear y/o actualizar los docentes de un curso.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param filtroFranjaHorariaDisponibleCursoDTO
	 * @return
	 */
	@PostMapping("/consultarFranjasHorariasDisponiblesPorCurso")
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
			@RequestBody FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO) {
		return this.gestionarPlanificacionManualCUIntPort
				.consultarFranjasHorariasDisponiblesPorCurso(filtroFranjaHorariaDisponibleCursoDTO);
	}

	/**
	 * Método encargado de obtener las franjas horarias de un curso dado el
	 * identificador del curso
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param eliminarHorarioDTO Información necesaria para eliminar el horario de
	 *                           un programa
	 * @return Booleano que indica si se eliminó con exito el horario
	 */
	@PostMapping("/eliminarHorarioPrograma")
	public Boolean eliminarHorarioPrograma(@RequestBody EliminarHorarioInDTO eliminarHorarioDTO) {
		return this.gestionarPlanificacionManualCUIntPort.eliminarHorarioPrograma(eliminarHorarioDTO);
	}
	
	/*************************************************
	 * Consultar horarios de Espacio físico y Docente
	 *************************************************/

	/**
	 * Método encargado de obtener todas las franjas horarias de un docente
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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