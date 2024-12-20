package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEntity;

public interface EstadoReservaRepository extends JpaRepository<EstadoReservaEntity, Long>{

}
