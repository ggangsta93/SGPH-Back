package co.edu.unicauca.sgph.seguridad.usuario.aplication.output;

import co.edu.unicauca.sgph.seguridad.usuario.domain.model.Usuario;

public interface UsuarioFormatterResultsIntPort {
	Usuario prepararRespuestaFallida(String error);
}
