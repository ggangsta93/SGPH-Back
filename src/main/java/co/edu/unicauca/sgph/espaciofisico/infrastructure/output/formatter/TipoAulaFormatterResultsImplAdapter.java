package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.TipoAulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;

@Service
public class TipoAulaFormatterResultsImplAdapter implements TipoAulaFormatterResultsIntPort {

	@Override
	public TipoAula prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
