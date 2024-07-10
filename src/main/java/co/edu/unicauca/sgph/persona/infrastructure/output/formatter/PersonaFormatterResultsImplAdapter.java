package co.edu.unicauca.sgph.persona.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.persona.aplication.output.PersonaFormatterResultsIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;

@Service
public class PersonaFormatterResultsImplAdapter implements PersonaFormatterResultsIntPort {

	@Override
	public Persona prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
