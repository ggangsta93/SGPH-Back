package co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param filtroCursoPlanificacionDTO
	 * @return
	 */
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO);

	/**
	 * Método encargado de consultar la información gneral de los cursos de un
	 * programa dado el identificador del programa
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idPrograma DTO Identificador del programa
	 * @return
	 */
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma);

	/**
	 * Método encargado de validar si existe cruces con horarios de docentes de un
	 * curso dado una franja horaria. Si no retorna resultados es porque no hay
	 * cruces, caso contrario retorna las franjas solapadas.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 */
	public List<Object[]> consultarCruceHorarioDocente(Long idCurso, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin);

	/**
	 * Método encargado de validar si existe cruces en espacios físicos dado una
	 * franja horaria. Si no retorna resultados es porque no hay cruces, caso
	 * contrario retorna las franjas solapadas.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEspacioFisico
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 */
	public List<Horario> consultarCruceHorarioEspacioFisico(List<Long> lstIdEspacioFisico, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin);

	/**
	 * Método encargado de validar si existe cruces con el horario del docente dado
	 * una franja horaria y el identificador de la persona
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPersona
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @return
	 */
	public List<Object[]> consultarCruceHorarioDocentePorIdPersona(Long idPersona, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin);

	/**
	 * Método encargado de obtener los nombres completos de cada espacio físico.
	 * Ejemplo del formato: 'Salón 204-Edificio nuevo'
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso();

	/**
	 * Método encargado de obtener las franjas horarias de un curso dado el
	 * identificador del curso
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso);

	/**
	 * Método encargado de obtener todas las franjas horarias de un docente
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPersona
	 * @return
	 */
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona);

	/**
	 * Método encargado de obtener todas las franjas horarias de un espacio físico
	 *
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico);

	/**
	 * Método encargado de consultar las franjas horarias de los espacios físicos.
	 * EL usuario puede filtrar los espacios físicos por: Ubicación, tipo espacio
	 * (Salón y/o Sala) físico, agrupadores o salón(Coincidencia por nombre) <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @param listaUbicaciones
	 * @param listaIdAgrupadorEspacioFisico
	 * @param listaIdTipoEspacioFisico
	 * @param salon
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(Long idCurso,
			List<String> listaUbicaciones, List<Long> listaIdTipoEspacioFisico,
			List<Long> listaIdAgrupadorEspacioFisico, String salon);

	/**
	 * Método encargado de consultar todas las franjas horarias del docente o
	 * docentes que imparten el curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeDocentesAsociadosACurso(Long idCurso);

	/**
	 * Método encargado de consultar todas las franjas horarias de los cursos de un
	 * semestre partiendo del curso que ingresa por parámetro <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @param idAsignatura
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeSemestrePorCurso(Long idCurso, Long idAsignatura);

	/**
	 * Método encargado de consultar las franajas horarias del curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeCursoPorCurso(Long idCurso);

	/**
	 * Método encargado de consultar la asignatura a la que pertenece un curso, su
	 * cupo y la cantidad de horas por semana<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public Object consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(Long idCurso);

}
