package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.UbicacionEntity;

public interface UbicacionRepositoryInt extends JpaRepository<UbicacionEntity, Long> {
	Optional<UbicacionEntity> findByNombre(String nombre);
}