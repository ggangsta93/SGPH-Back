package co.edu.unicauca.sgph.asignatura.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

@Service
public class AsignaturaFormatterResultsImplAdapter implements AsignaturaFormatterResultsIntPort {

	@Override
	public Asignatura prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}
}