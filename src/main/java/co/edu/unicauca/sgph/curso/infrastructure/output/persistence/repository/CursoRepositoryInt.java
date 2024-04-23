package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
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
	 * Método encargado de obtener el curso por su identificador
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public CursoEntity findByIdCurso(Long idCurso);
}
