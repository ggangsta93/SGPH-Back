package co.edu.unicauca.sgph.aula.aplication.output;

import co.edu.unicauca.sgph.aula.domain.model.Aula;

public interface AulaFormatterResultsIntPort {
	Aula prepararRespuestaFallida(String error);
}