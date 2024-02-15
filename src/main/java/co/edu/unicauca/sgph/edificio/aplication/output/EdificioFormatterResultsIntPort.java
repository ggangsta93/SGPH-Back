package co.edu.unicauca.sgph.edificio.aplication.output;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;

public interface EdificioFormatterResultsIntPort {
	Edificio prepararRespuestaFallida(String error);
}
