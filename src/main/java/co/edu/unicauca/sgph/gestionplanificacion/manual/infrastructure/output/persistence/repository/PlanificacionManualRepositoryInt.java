package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

public interface PlanificacionManualRepositoryInt extends JpaRepository<HorarioEntity, Long> {
	
	@Query("SELECT d.idPersona, c.idCurso "
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
			 + "AND h.horaFin > :horaInicio ")
		public List<Object[]> consultarCruceHorarioDocente(@Param("idCurso") Long idCurso, @Param("dia") DiaSemanaEnum dia,
				@Param("horaInicio") LocalTime horaInicio, @Param("horaFin") LocalTime horaFin);

		@Query("SELECT h "
				 + "FROM HorarioEntity h JOIN FETCH h.espaciosFisicos espaciosFisicos "
				 + "WHERE espaciosFisicos.idEspacioFisico IN (:lstIdEspacioFisico) "
				 + "AND h.dia = :dia "
				 + "AND h.horaInicio < :horaFin "
				 + "AND h.horaFin > :horaInicio ")
		public List<HorarioEntity> consultarCruceHorarioEspacioFisico(@Param("lstIdEspacioFisico") List<Long> lstIdEspacioFisico,
				@Param("dia") DiaSemanaEnum dia, @Param("horaInicio") LocalTime horaInicio,
				@Param("horaFin") LocalTime horaFin);
		
		
		@Query("SELECT d.idPersona, c.idCurso "
				 + "FROM HorarioEntity h JOIN h.curso c JOIN c.docentes d "
				 + "WHERE d.idPersona IN (:idPersona)"
				 + "AND h.dia = :dia "
				 + "AND h.horaInicio < :horaFin "
				 + "AND h.horaFin > :horaInicio ")
		public List<Object[]> consultarCruceHorarioDocentePorIdPersona(@Param("idPersona") Long idPersona,
				@Param("dia") DiaSemanaEnum dia, @Param("horaInicio") LocalTime horaInicio,
				@Param("horaFin") LocalTime horaFin);
		
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
				+ " WHERE hor.curso.idCurso = :idCurso")
		public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso);
		
		/**
		 * Método encargado de obtener todas las franjas horarias de un docente
		 * 
		 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
		 * 
		 * @param idPersona
		 * @return
		 */
		@Query("SELECT new co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO("
				+ "hor.dia, hor.horaInicio, hor.horaFin, CONCAT(pro.abreviatura,'-',asi.nombre,' ',cur.grupo)"
				+ ")"
				+ "FROM HorarioEntity hor "
				+ "JOIN hor.curso cur "
				+ "JOIN cur.docentes docentes "
				+ "JOIN cur.asignatura asi "
				+ "JOIN asi.programa pro "
				+ "WHERE docentes.idPersona =:idPersona "
				+ "AND (cur.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico) ")
		public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona, Long idPeriodoAcademico);
		
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
				+ " CONCAT(pro.abreviatura,'-',asi.nombre,' ',cur.grupo) "
				+ " ) "
				+ " FROM HorarioEntity hor "
				+ " JOIN hor.curso cur "
				+ " JOIN cur.asignatura asi "
				+ " JOIN asi.programa pro "
				+ " JOIN hor.espaciosFisicos espaciosFisicos "
				+ " WHERE espaciosFisicos.idEspacioFisico = :idEspacioFisico "
				+ " AND (cur.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico) "
				+ " ORDER BY hor.dia, hor.horaInicio ")
		public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico, Long idPeriodoAcademico);
		
		@Query("SELECT asignatura.idAsignatura, curso.cupo, asignatura.horasSemana "
				+ " FROM CursoEntity curso "
				+ " JOIN curso.asignatura asignatura "	
				+ " WHERE curso.idCurso = :idCurso ")
		public Object consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(Long idCurso);
}