package co.edu.unicauca.sgph.docente.aplication.output;

import co.edu.unicauca.sgph.docente.domain.model.Docente;

public interface DocenteFormatterResultsIntPort {
	Docente prepararRespuestaFallida(String error);
}
