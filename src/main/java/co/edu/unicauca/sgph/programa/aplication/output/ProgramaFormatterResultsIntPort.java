package co.edu.unicauca.sgph.programa.aplication.output;

import co.edu.unicauca.sgph.programa.domain.model.Programa;

public interface ProgramaFormatterResultsIntPort {
	Programa prepararRespuestaFallida(String error);
}
