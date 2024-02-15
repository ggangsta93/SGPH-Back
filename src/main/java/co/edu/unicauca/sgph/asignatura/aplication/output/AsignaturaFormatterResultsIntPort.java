package co.edu.unicauca.sgph.asignatura.aplication.output;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

public interface AsignaturaFormatterResultsIntPort {
	Asignatura prepararRespuestaFallida(String error);
}
