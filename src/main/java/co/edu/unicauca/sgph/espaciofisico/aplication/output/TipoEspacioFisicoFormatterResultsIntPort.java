package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;

public interface TipoEspacioFisicoFormatterResultsIntPort {
	TipoEspacioFisico prepararRespuestaFallida(String error);
}