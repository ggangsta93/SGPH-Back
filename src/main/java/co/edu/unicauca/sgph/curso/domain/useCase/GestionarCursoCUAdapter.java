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

	@Override
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura) {
		return this.gestionarCursoGatewayIntPort.consultarCursoPorGrupoYAsignatura(grupo, idAsignatura);
	}

	@Override
	public Curso guardarCurso(Curso curso) {
		return this.gestionarCursoGatewayIntPort.guardarCurso(curso);
	}

	@Override
	public Curso consultarCursoPorIdCurso(Long idCurso) {
		return this.gestionarCursoGatewayIntPort.consultarCursoPorIdCurso(idCurso);
	}

}