package co.edu.unicauca.sgph.persona.aplication.output;

import co.edu.unicauca.sgph.persona.domain.model.Persona;

public interface PersonaFormatterResultsIntPort {
	Persona prepararRespuestaFallida(String error);
}
