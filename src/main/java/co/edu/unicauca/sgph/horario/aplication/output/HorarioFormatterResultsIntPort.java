package co.edu.unicauca.sgph.horario.aplication.output;

import org.springframework.http.HttpStatus;

import co.edu.unicauca.sgph.horario.domain.model.Horario;

public interface HorarioFormatterResultsIntPort {
	Horario prepararRespuestaFallida(HttpStatus httpStatusEnum, String error);
}
