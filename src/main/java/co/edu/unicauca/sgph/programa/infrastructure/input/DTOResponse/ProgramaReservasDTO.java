package co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.EstadoUsuarioEnum;

public class ProgramaReservasDTO {
	private String rol;
	private String programaDepartamento;
	private String codigo;
	private String nombre;
	private EstadoUsuarioEnum estado;
	
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getProgramaDepartamento() {
		return programaDepartamento;
	}
	public void setProgramaDepartamento(String programaDepartamento) {
		this.programaDepartamento = programaDepartamento;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public EstadoUsuarioEnum getEstado() {
		return estado;
	}
	public void setEstado(EstadoUsuarioEnum estado) {
		this.estado = estado;
	}
	
	
}
