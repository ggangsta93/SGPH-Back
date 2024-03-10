package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;

public interface TipoEspacioFisicoRepositoryInt extends JpaRepository<TipoEspacioFisicoEntity, Long> {
	
}
