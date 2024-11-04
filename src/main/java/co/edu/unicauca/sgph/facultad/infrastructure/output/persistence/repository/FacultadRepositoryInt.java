package co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;

public interface FacultadRepositoryInt extends JpaRepository<FacultadEntity, Long> {
	
	public FacultadEntity findByNombre(String nombre);
    
	/**
	 * Método encargado de consultar todas las facultades<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<FacultadEntity> findAll();
	
	@Query("SELECT fac "
			+ " FROM FacultadEntity fac "
			+ " WHERE fac.idFacultad = :idFacultad ")
	public FacultadEntity consultarFacultadPorId(@Param("idFacultad") Long idFacultad);
}
