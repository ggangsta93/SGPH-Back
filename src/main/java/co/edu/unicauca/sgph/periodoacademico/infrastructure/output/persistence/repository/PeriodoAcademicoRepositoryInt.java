package co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;

public interface PeriodoAcademicoRepositoryInt extends JpaRepository<PeriodoAcademicoEntity, Long> {

	@Modifying
	@Query("UPDATE PeriodoAcademicoEntity p SET p.estado = 'CERRADO' WHERE (:fechaActual < p.fechaInicioPeriodo OR :fechaActual > p.fechaFinPeriodo) AND p.estado = 'ABIERTO'")
	public void actualizarEstadoSiFechaActualNoEnRango(@Param("fechaActual") Date fechaActual);

	@Query("SELECT p FROM PeriodoAcademicoEntity p WHERE p.estado = 'ABIERTO'")
	public PeriodoAcademicoEntity consultarPeriodoAcademicoVigente();

	@Query("SELECT p FROM PeriodoAcademicoEntity p WHERE p.anio = :anio AND p.periodo = :periodo")
	public PeriodoAcademicoEntity existsByAnioAndPeriodo(@Param("anio") Long anio, @Param("periodo") Long periodo);
}