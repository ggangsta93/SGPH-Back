package co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;

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
	 * Método encargado de obtener las franjas disponibles de un curso dado un
	 * conjunto de criterios de busqueda; este método considera los horarios de los
	 * docentes y espacios físicos.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param filtroFranjaHorariaDisponibleCursoDTO
	 * @return
	 */
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO);

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
	 * Método encargado de consultar los espacios físicos adecuados para un
	 * determinado curso. Se filtran los espacios físicos por: Ubicación,
	 * agrupadores o salón <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(Long idCurso,
			List<String> listaUbicaciones, List<Long> listaIdAgrupadorEspacioFisico, String salon);
	
	
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeDocentesAsociadosACurso(Long idCurso);
	
	
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeSemestrePorCurso(Long idCurso);

	
	
}
