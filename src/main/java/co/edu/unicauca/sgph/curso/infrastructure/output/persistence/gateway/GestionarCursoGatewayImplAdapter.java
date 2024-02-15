package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.gateway;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.repository.CursoRepositoryInt;

@Service
public class GestionarCursoGatewayImplAdapter implements GestionarCursoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private CursoRepositoryInt cursoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarCursoGatewayImplAdapter(CursoRepositoryInt cursoRepositoryInt, ModelMapper modelMapper) {
		this.cursoRepositoryInt = cursoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.modelMapper.map(
				this.cursoRepositoryInt.findByGrupoAndAsignatura(grupo, new AsignaturaEntity(idAsignatura)),
				Curso.class);
	}

	@Override
	public Curso guardarCurso(Curso curso) {		
		return this.modelMapper.map(this.cursoRepositoryInt.save(this.modelMapper.map(curso, CursoEntity.class)),
				Curso.class);
	}

	@Override
	public Curso consultarCursoPorIdCurso(Long idCurso) {
		return this.modelMapper.map(this.cursoRepositoryInt.findByIdCurso(idCurso), Curso.class);
	}

}
