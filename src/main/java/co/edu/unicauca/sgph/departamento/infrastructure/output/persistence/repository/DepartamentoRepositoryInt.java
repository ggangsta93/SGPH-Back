package co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity.DepartamentoEntity;

public interface DepartamentoRepositoryInt extends JpaRepository<DepartamentoEntity, Long> {
	Optional<DepartamentoEntity> findByNombre(String nombreDepartamento);
	
	@Query("SELECT dep "
			+ " FROM DepartamentoEntity dep "
			+ " WHERE dep.nombre = :nombre ")
    public DepartamentoEntity consultarDepartamentoPorNombre(@Param("nombre") String nombre);
}