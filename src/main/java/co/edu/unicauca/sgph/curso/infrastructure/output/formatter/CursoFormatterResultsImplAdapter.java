package co.edu.unicauca.sgph.curso.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;

@Service
public class CursoFormatterResultsImplAdapter implements CursoFormatterResultsIntPort {

	@Override
	public Curso prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}

}
