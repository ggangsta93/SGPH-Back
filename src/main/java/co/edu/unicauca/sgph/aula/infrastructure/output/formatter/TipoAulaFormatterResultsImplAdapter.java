package co.edu.unicauca.sgph.aula.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.aula.aplication.output.TipoAulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;

@Service
public class TipoAulaFormatterResultsImplAdapter implements TipoAulaFormatterResultsIntPort {

	@Override
	public TipoAula prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
