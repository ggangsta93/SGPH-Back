package co.edu.unicauca.sgph.edificio.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.edificio.aplication.output.EdificioFormatterResultsIntPort;
import co.edu.unicauca.sgph.edificio.domain.model.Edificio;

@Service
public class EdificioFormatterResultsImplAdapter implements EdificioFormatterResultsIntPort {

	@Override
	public Edificio prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
