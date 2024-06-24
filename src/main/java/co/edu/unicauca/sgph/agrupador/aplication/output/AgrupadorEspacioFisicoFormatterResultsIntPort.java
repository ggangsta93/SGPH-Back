package co.edu.unicauca.sgph.agrupador.aplication.output;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;

public interface AgrupadorEspacioFisicoFormatterResultsIntPort {
	AgrupadorEspacioFisico prepararRespuestaFallida(String error);
}