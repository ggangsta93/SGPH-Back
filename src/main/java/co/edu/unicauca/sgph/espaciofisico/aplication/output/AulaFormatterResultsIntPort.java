package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;

public interface AulaFormatterResultsIntPort {
	Aula prepararRespuestaFallida(String error);
}