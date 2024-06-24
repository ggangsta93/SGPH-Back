package co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity.DepartamentoEntity;

public interface DepartamentoRepositoryInt extends JpaRepository<DepartamentoEntity, Long> {

}