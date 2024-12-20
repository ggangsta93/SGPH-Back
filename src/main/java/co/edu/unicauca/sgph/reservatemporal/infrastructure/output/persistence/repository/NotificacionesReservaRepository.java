package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.NotificacionesReservaEntity;

public interface NotificacionesReservaRepository extends JpaRepository<NotificacionesReservaEntity, Long>{

}
