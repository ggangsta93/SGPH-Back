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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idPrograma                DTO Identificador del programa
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma,
			Long idPeriodoAcademicoVigente);

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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * @return
	 */
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona,
			Long idPeriodoAcademicoVigente);

	/**
	 * Método encargado de obtener todas las franjas horarias de un espacio físico
	 *
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * (Salón y/o Sala) físico, agrupadores o salón(Coincidencia por nombre) <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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

	/**
	 * Método encargado de eliminar todo el horario de un programa</br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param eliminarHorarioInDTO      Información necesaria para eliminar el
	 *                                  horario de un programa
	 * @param idPeriodoAcademicoVigente Periodo académico vigente
	 * 
	 */
	public void eliminarHorarioPrograma(EliminarHorarioInDTO eliminarHorarioInDTO, Long idPeriodoAcademicoVigente);

}
