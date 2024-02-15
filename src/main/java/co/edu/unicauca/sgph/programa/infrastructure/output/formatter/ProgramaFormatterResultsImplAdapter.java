package co.edu.unicauca.sgph.programa.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.programa.aplication.output.ProgramaFormatterResultsIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;

@Service
public class ProgramaFormatterResultsImplAdapter implements ProgramaFormatterResultsIntPort {

	@Override
	public Programa prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
