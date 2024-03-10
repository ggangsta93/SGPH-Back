package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;

public interface EspacioFisicoFormatterResultsIntPort {
	EspacioFisico prepararRespuestaFallida(String error);
}