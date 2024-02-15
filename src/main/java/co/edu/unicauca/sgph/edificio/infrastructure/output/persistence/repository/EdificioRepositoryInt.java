package co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.entity.EdificioEntity;

public interface EdificioRepositoryInt extends JpaRepository<EdificioEntity, Long> {
	
	public EdificioEntity findByNombre(String nombre);
	
	/**
	 * Método encargado de consultar los edificios asociados a una lista de
	 * facultades </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	@Query("SELECT edi FROM EdificioEntity edi WHERE edi.facultad.idFacultad IN (:lstIdFacultad)")
	public List<EdificioEntity> consultarEdificiosPorIdFacultad(@Param("lstIdFacultad") List<Long> lstIdFacultad);
}
