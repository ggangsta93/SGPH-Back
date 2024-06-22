package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

public interface PlanificacionManualRepositoryInt extends JpaRepository<HorarioEntity, Long> {
	
	
	@Query("SELECT d.idPersona, c.idCurso, TRIM(CONCAT(d.primerNombre,' ', COALESCE(d.segundoNombre,''),' ', d.primerApellido,' ', COALESCE(d.segundoApellido,'')))"
			 + "FROM HorarioEntity h JOIN h.curso c JOIN c.docentes d "
			 + "WHERE c.idCurso IN (SELECT c.idCurso "
			 + "					   FROM DocenteEntity d "
			 + "					   JOIN d.cursos c "
			 + "					   WHERE d.idPersona IN (SELECT d.idPersona "
			 + "											 FROM DocenteEntity d "
			 + "											 JOIN d.cursos c "
			 + "											 WHERE c.idCurso = :idCurso ))"
			 + "AND d.idPersona IN (SELECT d.idPersona "
			 + "					   FROM DocenteEntity d "
			 + "					   JOIN d.cursos c "
			 + "					   WHERE c.idCurso = :idCurso )"
			 + "AND h.dia = :dia "
			 + "AND h.horaInicio < :horaFin "
			 + "AND h.horaFin > :horaInicio "
			 + "AND h.curso.idCurso IN (SELECT curso.idCurso FROM CursoEntity curso WHERE curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente)")
		public List<Object[]> consultarCruceHorarioDocente(Long idCurso, DiaSemanaEnum dia, LocalTime horaInicio,
				LocalTime horaFin, Long idPeriodoAcademicoVigente);

		@Query("SELECT h "
				 + "FROM HorarioEntity h JOIN FETCH h.espaciosFisicos espaciosFisicos "
				 + "WHERE espaciosFisicos.idEspacioFisico IN (:lstIdEspacioFisico) "
				 + "AND h.dia = :dia "
				 + "AND h.horaInicio < :horaFin "
				 + "AND h.horaFin > :horaInicio "
				 + "AND h.curso.idCurso IN (SELECT curso.idCurso FROM CursoEntity curso WHERE curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente)")
		public List<HorarioEntity> consultarCruceHorarioEspacioFisico(List<Long> lstIdEspacioFisico, DiaSemanaEnum dia,
				LocalTime horaInicio, LocalTime horaFin, Long idPeriodoAcademicoVigente);
		
		
		@Query("SELECT d.idPersona, c.idCurso "
				 + "FROM HorarioEntity h JOIN h.curso c JOIN c.docentes d "
				 + "WHERE d.idPersona IN (:idPersona)"
				 + "AND h.dia = :dia "
				 + "AND h.horaInicio < :horaFin "
				 + "AND h.horaFin > :horaInicio "
				 + "AND h.curso.idCurso IN (SELECT curso.idCurso FROM CursoEntity curso WHERE curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente)")
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
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO( "
		        + " espacioFisico.idEspacioFisico, espacioFisico.salon"
		        + " ) "
		        + " FROM EspacioFisicoEntity espacioFisico "
		        + " LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico "
		        + " ORDER BY espacioFisico.idEspacioFisico ")
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
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO( "
				+ " hor.idHorario, espaciosFisicos.idEspacioFisico, hor.dia, hor.horaInicio, hor.horaFin "
				+ " ) "
				+ " FROM HorarioEntity hor "
				+ " JOIN hor.espaciosFisicos espaciosFisicos "
				+ " WHERE hor.curso.idCurso = :idCurso "
				+ " AND espaciosFisicos.idEspacioFisico IN ( SELECT horEsp.espacioFisico.idEspacioFisico "
				+ "											 FROM HorarioEspacioEntity horEsp "
				+ "									         WHERE horEsp.horario.idHorario = hor.idHorario "
				+ "											 AND horEsp.esPrincipal = :esPrincipal )"
				+ "")
		public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso, Boolean esPrincipal);
		
		/**
		 * Método encargado de obtener todas las franjas horarias de un docente
		 * 
		 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
		 * 
		 * @param idPersona
		 * @param idPeriodoAcademicoVigente
		 * @return
		 */
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO("
				+ "hor.idHorario, hor.dia, hor.horaInicio, hor.horaFin, CONCAT(pro.abreviatura,'-',asi.nombre,' ',cur.grupo)"
				+ ")"
				+ "FROM HorarioEntity hor "
				+ "JOIN hor.curso cur "
				+ "JOIN cur.docentes docentes "
				+ "JOIN cur.asignatura asi "
				+ "JOIN asi.programa pro "
				+ "WHERE docentes.idPersona =:idPersona "
				+ "AND (cur.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademicoVigente) ")
		public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona, Long idPeriodoAcademicoVigente);
		
		/**
		 * Método encargado de obtener todas las franjas horarias de un espacio físico
		 * 
		 * 
		 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
		 * 
		 * @param idEspacioFisico
		 * @return
		 */
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO( "
				+ " espaciosFisicos.idEspacioFisico, hor.dia, hor.horaInicio, hor.horaFin, "
				+ " CONCAT(pro.abreviatura,'-',asi.nombre,' ',cur.grupo), ( SELECT horEsp.esPrincipal "
				+ "															FROM HorarioEspacioEntity horEsp "
				+ "												            WHERE horEsp.horario.idHorario = hor.idHorario "
				+ "															AND horEsp.espacioFisico.idEspacioFisico = :idEspacioFisico )"
				+ " ) "
				+ " FROM HorarioEntity hor "
				+ " JOIN hor.curso cur "
				+ " JOIN cur.asignatura asi "
				+ " JOIN asi.programa pro "
				+ " JOIN hor.espaciosFisicos espaciosFisicos "
				+ " WHERE espaciosFisicos.idEspacioFisico = :idEspacioFisico "
				+ " AND (cur.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademicoVigente) "
				+ " ORDER BY hor.dia, hor.horaInicio ")
		public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico, Long idPeriodoAcademicoVigente);
		
		@Query("SELECT asignatura.idAsignatura, curso.cupo, asignatura.horasSemana "
				+ " FROM CursoEntity curso "
				+ " JOIN curso.asignatura asignatura "	
				+ " WHERE curso.idCurso = :idCurso ")
		public Object consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(Long idCurso);
		
		@Modifying
		@Query("DELETE FROM HorarioEspacioEntity he "
				+ "WHERE he.horario.idHorario IN ( SELECT h.idHorario "
				+ "								   FROM HorarioEntity h "
				+ "								   WHERE h.curso.idCurso IN (SELECT c.idCurso "
				+ " 														 FROM CursoEntity c "
				+ " 														 WHERE c.asignatura.idAsignatura IN (SELECT a.idAsignatura  "
				+ "                                     						 								 FROM AsignaturaEntity a "
				+ "                                    															 WHERE a.programa.idPrograma = :idPrograma) "
				+ "                         								 AND c.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente) "
				+ ")")
		public void eliminarRegistrosHorarioEspacioEntityPorProgramaYPeriodoAcademico(Long idPrograma, Long idPeriodoAcademicoVigente);
		
		@Modifying
		@Query("DELETE FROM HorarioEntity h "
				+ "WHERE h.curso.idCurso IN (SELECT c.idCurso "
				+ " 						 FROM CursoEntity c "
				+ " 						 WHERE c.asignatura.idAsignatura IN (SELECT a.idAsignatura  "
				+ "                                     						 FROM AsignaturaEntity a "
				+ "                                  						     WHERE a.programa.idPrograma = :idPrograma) "
				+ " 						 AND c.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente)")
		public void eliminarRegistrosHorarioEntityPorProgramaYPeriodoAcademico(Long idPrograma, Long idPeriodoAcademicoVigente);

		/**
		 * Método encargado de obtener las franjas horarias principales de un programa
		 * dados el identificador del programa y el periodo académico
		 * 
		 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
		 * 
		 * @param idPrograma
		 * @param idPeriodoAcademico
		 * @return
		 */
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO( "
				+ " horarios.idHorario, espaciosFisicos.idEspacioFisico, horarios.dia, horarios.horaInicio, horarios.horaFin "
				+ " ) "
				+ " FROM CursoEntity curso "
				+ " JOIN curso.asignatura asignatura "
				+ " JOIN curso.horarios horarios "
				+ " JOIN horarios.espaciosFisicos espaciosFisicos "
				+ " WHERE curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademico "
				+ " AND asignatura.programa.idPrograma = :idPrograma "
				+ " AND espaciosFisicos.idEspacioFisico IN ( SELECT horEsp.espacioFisico.idEspacioFisico "
				+ "											 FROM HorarioEspacioEntity horEsp "
				+ "									         WHERE horEsp.horario.idHorario = horarios.idHorario "
				+ "											 AND horEsp.esPrincipal = true )"
				+ "")
		public List<FranjaHorariaCursoDTO> consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(Long idPrograma, Long idPeriodoAcademico);
		
		
		/**
		 * Método encargado de consultar la cantidad de horas actual de un curso dado su
		 * identificador </br>
		 * 
		 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
		 * 
		 * @param idCurso
		 * @return
		 */		
		@Query("SELECT SUM(FUNCTION('TIME_TO_SEC', "
				+ " FUNCTION('TIMEDIFF', "
				+ " CONCAT(CURRENT_DATE, ' ', h.horaFin), "
				+ " CONCAT(CURRENT_DATE, ' ', h.horaInicio))) / 3600) "
				+ " FROM HorarioEntity h "
				+ "	WHERE h.curso.idCurso = :idCurso"
				+ "	GROUP BY h.curso.idCurso "
				+ "")
		public Long consultarCantidadHorasActualCurso(Long idCurso);
}