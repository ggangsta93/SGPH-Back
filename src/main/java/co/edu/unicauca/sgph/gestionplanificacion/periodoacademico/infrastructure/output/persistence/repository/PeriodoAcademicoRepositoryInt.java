package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;

public interface PeriodoAcademicoRepositoryInt extends JpaRepository<PeriodoAcademicoEntity, Long> {

}