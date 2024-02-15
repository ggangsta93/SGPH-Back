package co.edu.unicauca.sgph.seguridad.usuario.domain.useCase;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.seguridad.usuario.aplication.input.GestionarUsuarioCUIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.UsuarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;

public class GestionarUsuarioCUAdapter implements GestionarUsuarioCUIntPort {

	private final GestionarUsuarioGatewayIntPort gestionarUsuarioGatewayIntPort;
	private final UsuarioFormatterResultsIntPort usuarioFormatterResultsIntPort;
	
	public GestionarUsuarioCUAdapter(GestionarUsuarioGatewayIntPort gestionarUsuarioGatewayIntPort,
			UsuarioFormatterResultsIntPort usuarioFormatterResultsIntPort) {
		this.gestionarUsuarioGatewayIntPort = gestionarUsuarioGatewayIntPort;
		this.usuarioFormatterResultsIntPort = usuarioFormatterResultsIntPort;
	}

	/** 
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.input.GestionarUsuarioCUIntPort#consultarUsuariosPorFiltro(co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO)
	 */
	@Override
	public Page<UsuarioOutDTO> consultarUsuariosPorFiltro(FiltroUsuarioDTO filtroUsuarioDTO) {
		return this.gestionarUsuarioGatewayIntPort.consultarUsuariosPorFiltro(filtroUsuarioDTO);
	}

	/** 
	 * @see co.edu.unicauca.sgph.seguridad.usuario.aplication.input.GestionarUsuarioCUIntPort#guardarUsuario(co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.UsuarioInDTO)
	 */
	@Override
	public Usuario guardarUsuario(Usuario usuario) {
		return this.gestionarUsuarioGatewayIntPort.guardarUsuario(usuario);
	}

}