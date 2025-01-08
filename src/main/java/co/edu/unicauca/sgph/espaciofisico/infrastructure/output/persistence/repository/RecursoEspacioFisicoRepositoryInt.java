package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.espaciofisico.domain.model.RecursoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoEspacioFisicoEntity;

public interface RecursoEspacioFisicoRepositoryInt extends JpaRepository<RecursoEspacioFisicoEntity, Long> {
    List<RecursoEspacioFisicoEntity> findByEspacioFisicoIdEspacioFisico(Long id);
    
    @Query("SELECT r FROM RecursoEspacioFisicoEntity r JOIN FETCH r.recursoFisico WHERE r.espacioFisico.idEspacioFisico = :idEspacioFisico")
    List<RecursoEspacioFisicoEntity> findRecursosByEspacioFisico(@Param("idEspacioFisico") Long idEspacioFisico);
}
