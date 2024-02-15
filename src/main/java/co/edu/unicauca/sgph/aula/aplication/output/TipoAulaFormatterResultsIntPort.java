package co.edu.unicauca.sgph.aula.aplication.output;

import co.edu.unicauca.sgph.aula.domain.model.TipoAula;

public interface TipoAulaFormatterResultsIntPort {
	TipoAula prepararRespuestaFallida(String error);
}