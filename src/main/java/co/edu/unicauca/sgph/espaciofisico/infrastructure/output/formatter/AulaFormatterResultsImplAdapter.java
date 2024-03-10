package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.AulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;

@Service
public class AulaFormatterResultsImplAdapter implements AulaFormatterResultsIntPort {

	@Override
	public Aula prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
