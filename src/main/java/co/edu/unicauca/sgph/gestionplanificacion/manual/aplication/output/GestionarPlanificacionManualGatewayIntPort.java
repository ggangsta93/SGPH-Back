package co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public interface GestionarPlanificacionManualGatewayIntPort {

	/**
	 * Método encargado de obtener los cursos dado un conjunto de criterios de
	 * busqueda.
	 * 
	 * @author apedro
	 * 
	 * @param filtroCursoPlanificacionDTO
	 * @param idPeriodoAcademicoVigente   Periodo académico vigente
	 * @return
	 */
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO, Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de consultar la información gneral de los cursos de un
	 * programa dado el identificador del programa
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma                DTO Identificador del programa
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma,
			Long idPeriodoAcademicoVigente);
	
	/**
	 * Método encargado de consultar la cantidad de horas actual de un curso dado su
	 * identificador </br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */		
	public Long consultarCantidadHorasActualCurso(Long idCurso);

	/**
	 * Método encargado de validar si existe cruces con horarios de docentes de un
	 * curso dado una franja horaria. Si no retorna resultados es porque no hay
	 * cruces, caso contrario retorna las franjas solapadas.
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<Object[]> consultarCruceHorarioDocente(Long idCurso, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin, Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de validar si existe cruces en espacios físicos dado una
	 * franja horaria. Si no retorna resultados es porque no hay cruces, caso
	 * contrario retorna las franjas solapadas.
	 * 
	 * @author apedro
	 * 
	 * @param lstIdEspacioFisico
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * 
	 * @return
	 */
	public List<Horario> consultarCruceHorarioEspacioFisico(List<Long> lstIdEspacioFisico, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin, Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de validar si existe cruces con el horario del docente dado
	 * una franja horaria y el identificador de la persona
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<Object[]> consultarCruceHorarioDocentePorIdPersona(Long idPersona, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin, Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de obtener los nombres completos de cada espacio físico.
	 * Ejemplo del formato: 'Salón 204-Edificio nuevo'
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso();

	/**
	 * Método encargado de obtener las franjas horarias de un curso dado el
	 * identificador del curso
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param esPrincipal Booleano que indica si se consultan las franjas
	 *                    principales, si está en 'false' se consultan los espacios
	 *                    físicos secundarios
	 * @return
	 */
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso, Boolean esPrincipal);

	/**
	 * Método encargado de obtener las franjas horarias principales de un programa
	 * dados el identificador del programa y el periodo académico
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param idPeriodoAcademico
	 * @return
	 */
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(
			Long idPrograma, Long idPeriodoAcademico);

	/**
	 * Método encargado de obtener todas las franjas horarias de un docente
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona,
			Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de obtener todas las franjas horarias de un espacio físico
	 *
	 * @author apedro
	 * 
	 * @param idEspacioFisico
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico,
			Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de consultar las franjas horarias de los espacios físicos.
	 * EL usuario puede filtrar los espacios físicos por: Ubicación, tipo espacio
	 * físico (Salón y/o Sala), agrupadores o salón(Coincidencia por nombre) <br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param listaIdUbicacion
	 * @param listaIdAgrupadorEspacioFisico
	 * @param listaIdTipoEspacioFisico
	 * @param salon
	 * @param idPeriodoAcademicoVigente     Periodo académico vigente
	 * @return
	 */
	public Map<Long, List<FranjaHorariaBasicaDTO>> consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(
			Long idCurso, List<Long> listaIdUbicacion, List<Long> listaIdTipoEspacioFisico,
			List<Long> listaIdAgrupadorEspacioFisico, String salon, Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de consultar todas las franjas horarias del docente o
	 * docentes que imparten el curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeDocentesAsociadosACurso(Long idCurso,
			Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de consultar todas las franjas horarias de los cursos de un
	 * semestre partiendo del curso que ingresa por parámetro <br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @param idAsignatura
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeSemestrePorCurso(Long idCurso, Long idAsignatura,
			Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de consultar las franajas horarias del curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeCursoPorCurso(Long idCurso);

	/**
	 * Método encargado de consultar la asignatura a la que pertenece un curso, su
	 * cupo y la cantidad de horas por semana<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public Object consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(Long idCurso);

	/**
	 * Método encargado de eliminar todo el horario de un programa</br>
	 * 
	 * @author apedro
	 * 
	 * @param eliminarHorarioInDTO      Información necesaria para eliminar el
	 *                                  horario de un programa
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * 
	 */
	public void eliminarHorarioPrograma(EliminarHorarioInDTO eliminarHorarioInDTO, Long idPeriodoAcademicoVigente);

}
