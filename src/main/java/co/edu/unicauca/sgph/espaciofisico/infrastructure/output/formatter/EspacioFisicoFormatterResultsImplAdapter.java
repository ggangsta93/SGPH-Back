package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.EspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;

@Service
public class EspacioFisicoFormatterResultsImplAdapter implements EspacioFisicoFormatterResultsIntPort {

	@Override
	public EspacioFisico prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
