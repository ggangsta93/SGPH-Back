package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;

public interface TipoEspacioFisicoRepositoryInt extends JpaRepository<TipoEspacioFisicoEntity, Long> {
	
	/**
	 * Método encargado de consultar los tipo de espacios físicos
	 * asociados a una lista de edificios<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstEdificio
	 * @return
	 */
	@Query("SELECT tipoEspacioFisico " +
		       "FROM TipoEspacioFisicoEntity tipoEspacioFisico " +
		       "WHERE tipoEspacioFisico in (" +
		       "    SELECT espacioFisico.tipoEspacioFisico " +
		       "    FROM EspacioFisicoEntity espacioFisico " +
		       "    WHERE espacioFisico.edificio in (:lstEdificio) " +
		       ")")
	public List<TipoEspacioFisicoEntity> consultarTiposEspaciosFisicosPorEdificio(List<String> lstEdificio);
}
