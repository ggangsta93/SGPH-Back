package co.edu.unicauca.sgph.usuario.aplication.output;

import co.edu.unicauca.sgph.usuario.domain.model.Usuario;

public interface UsuarioFormatterResultsIntPort {
	Usuario prepararRespuestaFallida(String error);
}
