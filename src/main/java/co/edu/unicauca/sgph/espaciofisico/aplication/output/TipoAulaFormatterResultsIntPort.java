package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;

public interface TipoAulaFormatterResultsIntPort {
	TipoAula prepararRespuestaFallida(String error);
}