package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.entity.LaborDocenciaEntity;

public interface LaborDocenciaRepositoryInt extends JpaRepository<LaborDocenciaEntity, Long> {

}
