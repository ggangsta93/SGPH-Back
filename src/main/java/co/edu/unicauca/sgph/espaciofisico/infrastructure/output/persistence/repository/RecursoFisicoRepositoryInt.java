package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoFisicoEntity;

public interface RecursoFisicoRepositoryInt extends JpaRepository<RecursoFisicoEntity, Long> {
}
