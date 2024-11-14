package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoEspacioFisicoEntity;

public interface RecursoEspacioFisicoRepositoryInt extends JpaRepository<RecursoEspacioFisicoEntity, Long> {
    List<RecursoEspacioFisicoEntity> findByEspacioFisicoIdEspacioFisico(Long id);
}
