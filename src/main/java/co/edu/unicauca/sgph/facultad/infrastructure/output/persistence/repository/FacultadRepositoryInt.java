package co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
