package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.EstadoReservaEnum;

public interface EstadoReservaRepository extends JpaRepository<EstadoReservaEntity, Long>{
	@Query("SELECT e FROM EstadoReservaEntity e WHERE e.descripcion = :descripcion")
    Optional<EstadoReservaEntity> findByDescripcion(@Param("descripcion") EstadoReservaEnum descripcion);
}
