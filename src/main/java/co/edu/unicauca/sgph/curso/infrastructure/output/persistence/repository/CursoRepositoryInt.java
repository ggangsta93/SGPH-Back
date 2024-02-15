package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;

public interface CursoRepositoryInt extends JpaRepository<CursoEntity, Long> {
	
	public CursoEntity findByGrupoAndAsignatura(String grupo, AsignaturaEntity asignatura);
	
	public CursoEntity findByIdCurso(Long idCurso);
}
