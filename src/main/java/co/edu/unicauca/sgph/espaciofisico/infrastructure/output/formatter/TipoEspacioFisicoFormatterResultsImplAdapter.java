package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.TipoEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;

@Service
public class TipoEspacioFisicoFormatterResultsImplAdapter implements TipoEspacioFisicoFormatterResultsIntPort {

	@Override
	public TipoEspacioFisico prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
