package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoEspacioFisicoEntity;

public interface RecursoEspacioFisicoRepositoryInt extends JpaRepository<RecursoEspacioFisicoEntity, Long> {
    List<RecursoEspacioFisicoEntity> findByEspacioFisicoIdEspacioFisico(Long id);
    
    @Query("SELECT r FROM RecursoEspacioFisicoEntity r JOIN FETCH r.recursoFisico WHERE r.espacioFisico.idEspacioFisico = :idEspacioFisico")
    List<RecursoEspacioFisicoEntity> findRecursosByEspacioFisico(@Param("idEspacioFisico") Long idEspacioFisico);
    
    @Query("SELECT r FROM RecursoEspacioFisicoEntity r WHERE r.espacioFisico.idEspacioFisico = :idEspacioFisico AND r.recursoFisico.idRecursoFisico = :idRecursoFisico")
    Optional<RecursoEspacioFisicoEntity> findByEspacioFisicoAndRecursoFisico(@Param("idEspacioFisico") Long idEspacioFisico, @Param("idRecursoFisico") Long idRecursoFisico);

}
