package co.edu.unicauca.sgph.programa.infrastructure.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;

public interface ProgramaRepositoryInt extends JpaRepository<ProgramaEntity, Long> {

	Optional<ProgramaEntity> findByNombre(String nombre);

	/**
	 * Método encargado de consultar los programas asociados a una lista de
	 * facultades <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@Query("SELECT pro FROM ProgramaEntity pro WHERE pro.facultad.idFacultad IN (:lstIdFacultad)")
	public List<ProgramaEntity> consultarProgramasPorIdFacultad(@Param("lstIdFacultad") List<Long> lstIdFacultad);
	
	@Query("SELECT pro FROM ProgramaEntity pro WHERE pro.idPrograma = :idPrograma")
	public ProgramaEntity consultarProgramaPorId(Long idPrograma);
	
	
	@Query("SELECT pro FROM ProgramaEntity pro WHERE pro.idPrograma IN (:lstIdPrograma)")
	public List<ProgramaEntity> consultarProgramasPermitidosPorUsuario(List<Long> lstIdPrograma);
	
	@Query("SELECT pro FROM ProgramaEntity pro WHERE pro.nombre = :nombre")
	public ProgramaEntity BuscarPorNombre(String nombre);
}