package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecursoFisicoRepositoryInt extends JpaRepository<RecursoFisicoEntity, Long> {
}
