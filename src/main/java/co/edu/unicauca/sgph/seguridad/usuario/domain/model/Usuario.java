package co.edu.unicauca.sgph.seguridad.usuario.domain.model;

import java.util.Set;
import java.util.List;

import co.edu.unicauca.sgph.common.domain.model.Persona;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

public class Usuario extends Persona {

	private String nombreUsuario;

	private String password;

	private EstadoUsuarioEnum estado;
	
	private Set<Rol> roles;
	
	private List<Programa> programas;

	public Usuario() {
		// Constructor sin parámetros
	}

	public Usuario(Long idPersona) {
		super(idPersona);
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoUsuarioEnum estado) {
		this.estado = estado;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}
}