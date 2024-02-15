package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

public interface HorarioRepositoryInt extends JpaRepository<HorarioEntity, Long> {
					    	
	public List<HorarioEntity> findByCurso(CursoEntity curso);
}
