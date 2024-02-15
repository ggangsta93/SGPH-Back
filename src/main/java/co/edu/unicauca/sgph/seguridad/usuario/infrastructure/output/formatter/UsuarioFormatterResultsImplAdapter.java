package co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.formatter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.UsuarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.domain.model.Usuario;

@Service
public class UsuarioFormatterResultsImplAdapter implements UsuarioFormatterResultsIntPort {

	@Override
	public Usuario prepararRespuestaFallida(String error) {
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
	}
}
