package co.edu.unicauca.sgph.curso.infrastructure.output.persistence.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort#consultarCursoPorGrupoYAsignatura(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	@Deprecated
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.modelMapper.map(
				this.cursoRepositoryInt.findByGrupoAndAsignatura(grupo, new AsignaturaEntity(idAsignatura)),
				Curso.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort#guardarCurso(co.edu.unicauca.sgph.curso.domain.model.Curso)
	 */
	@Override
	public Curso guardarCurso(Curso curso) {
		return this.modelMapper.map(this.cursoRepositoryInt.save(this.modelMapper.map(curso, CursoEntity.class)),
				Curso.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort#consultarCursoPorIdCurso(java.lang.Long)
	 */
	@Override
	public Curso consultarCursoPorIdCurso(Long idCurso) {
		try {
			return this.modelMapper.map(this.cursoRepositoryInt.findByIdCurso(idCurso), Curso.class);
		} catch (Exception e) {
			/*
			 * Retorna nulo si no existe el curso (Genera excepción al intentar mapear algo nulo)
			 */
			return null;
		}

	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort#consultarAgrupadoresEspaciosFisicosPorCurso(java.lang.Long)
	 */
	@Override
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(Long idCurso) {
		return this.cursoRepositoryInt.consultarAgrupadoresEspaciosFisicosPorCurso(idCurso);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort#consultarCursosPorProgramaYPeriodoAcademico(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<Curso> consultarCursosPorProgramaYPeriodoAcademico(Long idPrograma, Long idPeriodoAcademico) {
		List<CursoEntity> lstCursoEntity = this.cursoRepositoryInt
				.consultarCursosPorProgramaYPeriodoAcademico(idPrograma, idPeriodoAcademico);
		return this.modelMapper.map(lstCursoEntity, new TypeToken<List<Curso>>() {
		}.getType());
	}

	@Override
	public Boolean eliminarCurso(Long id) {
		this.cursoRepositoryInt.deleteById(id);
		return Boolean.TRUE;
	}

}
