package co.edu.unicauca.sgph.docente.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;

@Service
public class DocenteFormatterResultsImplAdapter implements DocenteFormatterResultsIntPort {

	@Override
	public Docente prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
