package co.edu.unicauca.sgph.agrupador.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.agrupador.aplication.output.AgrupadorEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;

@Service
public class AgrupadorEspacioFisicoFormatterResultsImplAdapter
		implements AgrupadorEspacioFisicoFormatterResultsIntPort {

	@Override
	public AgrupadorEspacioFisico prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
