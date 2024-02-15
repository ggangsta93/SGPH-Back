package co.edu.unicauca.sgph.curso.aplication.output;

import co.edu.unicauca.sgph.curso.domain.model.Curso;

public interface CursoFormatterResultsIntPort {
	Curso prepararRespuestaFallida(String error);
}
