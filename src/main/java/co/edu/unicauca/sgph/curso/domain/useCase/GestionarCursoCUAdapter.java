package co.edu.unicauca.sgph.curso.domain.useCase;

import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;

public class GestionarCursoCUAdapter implements GestionarCursoCUIntPort {

	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private CursoFormatterResultsIntPort cursoFormatterResultsIntPort;

	public GestionarCursoCUAdapter(GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort) {
		this.gestionarCursoGatewayIntPort = gestionarCursoGatewayIntPort;
		this.cursoFormatterResultsIntPort = cursoFormatterResultsIntPort;
	}

	/** 
	 * @see co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort#consultarCursoPorGrupoYAsignatura(java.lang.String, java.lang.Long)
	 */
	@Override
	@Deprecated
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

}