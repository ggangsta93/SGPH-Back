package co.edu.unicauca.sgph.seguridad.usuario.domain.model;

import co.edu.unicauca.sgph.common.enums.RolUsuarioEnum;

public class Rol {

	private Long idRol;

	private RolUsuarioEnum rolUsuario;

	public Rol() {
		// Constructor sin parámetros
	}
	
	public Rol(Long idRol) {
		this.idRol = idRol;
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