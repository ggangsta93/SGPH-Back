package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;

public interface AsignaturaRepositoryInt extends JpaRepository<AsignaturaEntity, Long> {
		
	/**
	 * Método encargado de consultar las asignaturas por programa y estado<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param estadoAsignaturaEnum Estado de la asignatura (Si es nulo se obtienen
	 *                             todos)
	 * @return
	 */
	@Query("SELECT asi "
			+ " FROM AsignaturaEntity asi "
			+ " WHERE asi.programa.idPrograma =:idPrograma "
			+ " AND (:estadoAsignaturaEnum IS NULL OR asi.estado =:estadoAsignaturaEnum) "
			+ " ORDER BY asi.semestre ")
    public List<AsignaturaEntity> consultarAsignaturasPorIdProgramaYEstado(@Param("idPrograma") Long idPrograma, EstadoAsignaturaEnum estadoAsignaturaEnum);
		
	/**
	 * Método encargado de consultar las asignaturas de los cursos del periodo
	 * vigente de un programa<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma
	 * @param idPeriodoAcademicoVigente 
	 * @return
	 */
	@Query("SELECT DISTINCT(asi) "
			+ " FROM CursoEntity cur "
			+ " JOIN cur.asignatura asi "
			+ " WHERE asi.programa.idPrograma =:idPrograma "
			+ " AND cur.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademicoVigente "
			+ " ORDER BY asi.semestre ")
    public List<AsignaturaEntity> consultarAsignaturasDeLosCursosPorIdProgramaYPeriodoAcademicoVigente(Long idPrograma, Long idPeriodoAcademicoVigente);	
	

	Page<AsignaturaEntity> findByProgramaIdProgramaAndSemestre(Long idPrograma, Optional<Integer> semestre, Pageable pageable);

	Page<AsignaturaEntity> findByProgramaIdProgramaAndSemestreAndProgramaFacultadIdFacultad(Long idPrograma,
																							Integer semestre,
																							Long idFacultad,
																							Pageable pageable);
	Optional<AsignaturaEntity> findByOid(String OID);
	Optional<AsignaturaEntity> findFirstByOid(String oid);
	List<AsignaturaEntity> findByOidInAndEstado(List<String> oids, EstadoAsignaturaEnum estado);
	Optional<AsignaturaEntity> findByCodigoAsignatura(String codigoAsignatura);
	
	@Query("SELECT p.idPrograma FROM ProgramaEntity p WHERE p.nombre = :nombrePrograma")
	List<Long> consultarIdsPorNombrePrograma(@Param("nombrePrograma") String nombrePrograma);
	
	@Query("SELECT COUNT(a) > 0 FROM AsignaturaEntity a WHERE a.codigoAsignatura = :codigoAsignatura AND a.programa.idPrograma = :idPrograma")
	boolean existeAsignaturaPorCodigoYPrograma(@Param("codigoAsignatura") String codigoAsignatura, @Param("idPrograma") Long idPrograma);

	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AsignaturaEntity a WHERE a.oid = :oid")
	Boolean buscarAsignaturaOid(@Param("oid") String oid);
	
	@Query("SELECT COUNT(asi) FROM AsignaturaEntity asi")
	public Long contarAsignaturas();
}

