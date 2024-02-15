package co.edu.unicauca.sgph.facultad.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.facultad.aplication.output.FacultadFormatterResultsIntPort;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

@Service
public class FacultadFormatterResultsImplAdapter implements FacultadFormatterResultsIntPort {

	@Override
	public Facultad prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
