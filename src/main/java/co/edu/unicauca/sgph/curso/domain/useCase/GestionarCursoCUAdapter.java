package co.edu.unicauca.sgph.curso.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;

public class GestionarCursoCUAdapter implements GestionarCursoCUIntPort {

	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private CursoFormatterResultsIntPort cursoFormatterResultsIntPort;

	public GestionarCursoCUAdapter(GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort) {
		this.gestionarCursoGatewayIntPort = gestionarCursoGatewayIntPort;
		this.cursoFormatterResultsIntPort = cursoFormatterResultsIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort#consultarCursoPorGrupoYAsignatura(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.gestionarCursoGatewayIntPort.consultarCursoPorGrupoYAsignatura(grupo, idAsignatura);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort#guardarCurso(co.edu.unicauca.sgph.curso.domain.model.Curso)
	 */
	@Override
	public Curso guardarCurso(Curso curso) {
		return this.gestionarCursoGatewayIntPort.guardarCurso(curso);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort#consultarCursoPorIdCurso(java.lang.Long)
	 */
	@Override
	public Curso consultarCursoPorIdCurso(Long idCurso) {
		return this.gestionarCursoGatewayIntPort.consultarCursoPorIdCurso(idCurso);
	}

	/**
	 * @see co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort#consultarAgrupadoresEspaciosFisicosPorCurso(java.lang.Long)
	 */
	@Override
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(Long idCurso) {
		return this.gestionarCursoGatewayIntPort.consultarAgrupadoresEspaciosFisicosPorCurso(idCurso);
	}

	@Override
	public Curso obtenerCurso(Long id) {
		return this.gestionarCursoGatewayIntPort.consultarCursoPorIdCurso(id);
	}

	@Override
	public Boolean eliminarCurso(Long id) {
		return this.gestionarCursoGatewayIntPort.eliminarCurso(id);
	}

	@Override
	public List<Curso> consultarCursosPorIdPeriodoYIdPrograma(Long idPeriodo, Long idPrograma) {
		return  this.gestionarCursoGatewayIntPort.consultarCursosPorProgramaYPeriodoAcademico(idPrograma, idPeriodo);
	}
	
	@Override
	public Boolean existsCursoByGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.gestionarCursoGatewayIntPort.existsCursoByGrupoYAsignatura(grupo, idAsignatura);
	}

	@Override
	public Boolean existsCursoByGrupoYCupoYPeriodoYAsignatura(String grupo, Integer cupo, Long idPeriodo,
			Long idAsignatura) {
		return this.gestionarCursoGatewayIntPort.existsCursoByGrupoYCupoYPeriodoYAsignatura(grupo, cupo, idPeriodo, idAsignatura);
	}

	@Override
	public List<Curso> findCursoByGrupoYCupoYPeriodoYAsignatura(String grupo, Long idAsignatura) {
		return this.gestionarCursoGatewayIntPort.findCursoByGrupoYCupoYPeriodoYAsignatura(grupo, idAsignatura);
	}

	@Override
	public int actualizarCurso(PeriodoAcademicoEntity periodoAcademico, Integer cupo, String grupo, Long asignaturaId) {
		return this.gestionarCursoGatewayIntPort.actualizarCurso(periodoAcademico, cupo, grupo, asignaturaId);
	}

}