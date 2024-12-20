package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.LogReservasEntity;

public interface LogReservasRepository extends JpaRepository<LogReservasEntity, Long>{

}
