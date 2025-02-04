package co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest;

import java.io.Serializable;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

public class FiltroUsuarioDTO implements Serializable {

	private static final long serialVersionUID = -8457760909533623061L;

	private String nombre;

	private String numeroIdentificacion;

	private EstadoUsuarioEnum estado;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroUsuarioDTO() {
		// Constructor sin parámetros
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getRegistrosPorPagina() {
		return registrosPorPagina;
	}

	public void setRegistrosPorPagina(Integer registrosPorPagina) {
		this.registrosPorPagina = registrosPorPagina;
	}

	public EstadoUsuarioEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoUsuarioEnum estado) {
		this.estado = estado;
	}
}