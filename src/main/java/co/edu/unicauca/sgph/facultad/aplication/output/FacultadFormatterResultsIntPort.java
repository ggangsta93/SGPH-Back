package co.edu.unicauca.sgph.facultad.aplication.output;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public interface FacultadFormatterResultsIntPort {
	Facultad prepararRespuestaFallida(String error);
}
