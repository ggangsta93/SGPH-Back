package co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioReservasDTO;

public class FormularioReservaDTO {
	private UsuarioReservasDTO usuario;
    private List<EspacioFisicoOutDTO> espaciosFisicos;
    
	public UsuarioReservasDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioReservasDTO usuario) {
		this.usuario = usuario;
	}
	public List<EspacioFisicoOutDTO> getEspaciosFisicos() {
		return espaciosFisicos;
	}
	public void setEspaciosFisicos(List<EspacioFisicoOutDTO> espaciosFisicos) {
		this.espaciosFisicos = espaciosFisicos;
	}
}
