package co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.RolUsuarioEnum;

public class RolOutDTO {

	private Long idRol;

	private RolUsuarioEnum rolUsuario;
	
	public RolOutDTO() {
		//Constructor sin parametros
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public RolUsuarioEnum getRolUsuario() {
		return rolUsuario;
	}

	public void setRolUsuario(RolUsuarioEnum rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
}