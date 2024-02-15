package co.edu.unicauca.sgph.aula.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.aula.infrastructure.output.persistence.entity.TipoAulaEntity;

public interface TipoAulaRepositoryInt extends JpaRepository<TipoAulaEntity, Long> {
	
}
