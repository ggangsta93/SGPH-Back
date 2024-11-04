package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;

public interface CursoRepositoryInt extends JpaRepository<CursoEntity, Long> {

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author apedro
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	
	public CursoEntity findByGrupoAndAsignatura(String grupo, AsignaturaEntity asignatura);

	/**
	 * Método encargado de obtener el curso por su identificador<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public CursoEntity findByIdCurso(Long idCurso);
	
	
	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param cursoInDTO
	 * @return Lista de idAgrupadorEspacioFisico
	 */
	@Query("SELECT DISTINCT(agrupadores.idAgrupadorEspacioFisico)"
			+ " FROM CursoEntity curso "
			+ " JOIN curso.asignatura asignatura "
			+ " JOIN asignatura.agrupadores agrupadores "
			+ " WHERE curso.idCurso =:idCurso")
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(Long idCurso);

	/**
	 * Método encargado de consultar todos los cursos de un programa dado el
	 * programa y periodo académico </br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param idPeriodoAcademico
	 * @return
	 */
	@Query("SELECT curso " 
			+ " FROM CursoEntity curso "
			+ " JOIN curso.asignatura asignatura "
			+ " WHERE asignatura.programa.idPrograma = :idPrograma"
			+ " AND curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademico")
	public List<CursoEntity> consultarCursosPorProgramaYPeriodoAcademico(Long idPrograma, Long idPeriodoAcademico);
	
	@Query("SELECT curso " 
			+ " FROM CursoEntity curso "
			+ " JOIN curso.asignatura asignatura"			
			+ " WHERE curso.asignatura.idAsignatura = :idAsignatura "
			+ " AND asignatura.estado = co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum.ACTIVO")
	public List<CursoEntity> consultarCursoPorAsignaturaActiva(@Param("idAsignatura") Long idAsignatura);
	
	@Query("SELECT curso FROM CursoEntity curso " +
		       "WHERE curso.asignatura.idAsignatura = :idAsignatura " +
		       "AND curso.grupo = :grupo")
	public List<CursoEntity> consultarCursosPorAsignaturaYGrupo(@Param("idAsignatura") Long idAsignatura, @Param("grupo") String grupo);

	@Query("SELECT curso FROM CursoEntity curso " +
		       "WHERE curso.idCurso = :idCurso " +
		       "AND (size(curso.docentes) > 0 OR size(curso.horarios) > 0)")
	public List<CursoEntity> consultarExisteDocenteAsociadoAlCurso(@Param("idCurso") Long idCurso);
	
	@Query("SELECT c FROM CursoEntity c WHERE c.grupo = :grupo AND c.cupo = :cupo AND c.periodoAcademico.idPeriodoAcademico = :periodoAcademico AND c.asignatura.idAsignatura = :idAsignatura")
	public List<CursoEntity> existsCursoByGrupoYCupoYPeriodoYAsignatura(@Param("grupo") String grupo, @Param("cupo") Integer cupo, @Param("periodoAcademico") Long periodoAcademico, @Param("idAsignatura") Long idAsignatura);

}
