package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoFisicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoEspacioFisicoRepositoryInt extends JpaRepository<RecursoEspacioFisicoEntity, Long> {
}
