package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;

public interface AgrupadorEspacioFisicoFormatterResultsIntPort {
	AgrupadorEspacioFisico prepararRespuestaFallida(String error);
}