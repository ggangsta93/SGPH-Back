package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;

public interface CursoRepositoryInt extends JpaRepository<CursoEntity, Long> {

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	@Deprecated
	public CursoEntity findByGrupoAndAsignatura(String grupo, AsignaturaEntity asignatura);

	/**
	 * Método encargado de obtener el curso por su identificador<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public CursoEntity findByIdCurso(Long idCurso);
	
	
	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
}
