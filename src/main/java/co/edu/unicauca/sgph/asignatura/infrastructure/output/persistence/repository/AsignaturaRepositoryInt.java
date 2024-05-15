package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;

public interface AsignaturaRepositoryInt extends JpaRepository<AsignaturaEntity, Long> {
		
	/**
	 * Método encargado de obtener las materias de un programa
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPrograma
	 * @return
	 */
	@Query("SELECT asi "
			+ " FROM AsignaturaEntity asi "
			+ " WHERE asi.programa.idPrograma =:idPrograma "
			+ " ORDER BY asi.semestre ")
    public List<AsignaturaEntity> consultarAsignaturasPorIdPrograma(@Param("idPrograma") Long idPrograma);

	Page<AsignaturaEntity> findByProgramaIdProgramaAndSemestre(Long idPrograma, Optional<Integer> semestre, Pageable pageable);

	Page<AsignaturaEntity> findByProgramaIdProgramaAndSemestreAndProgramaFacultadIdFacultad(Long idPrograma,
																							Integer semestre,
																							Long idFacultad,
																							Pageable pageable);
}
