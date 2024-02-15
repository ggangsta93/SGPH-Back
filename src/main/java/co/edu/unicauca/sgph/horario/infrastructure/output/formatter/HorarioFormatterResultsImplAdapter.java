package co.edu.unicauca.sgph.horario.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.horario.aplication.output.HorarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

@Service
public class HorarioFormatterResultsImplAdapter implements HorarioFormatterResultsIntPort {

	@Override
	public Horario prepararRespuestaFallida(HttpStatus httpStatusEnum, String error) {
		throw new ResponseStatusException(httpStatusEnum, error);
	}

}