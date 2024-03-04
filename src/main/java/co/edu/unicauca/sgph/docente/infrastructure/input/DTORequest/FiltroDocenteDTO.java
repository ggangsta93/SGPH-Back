package co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest;

import java.io.Serializable;

import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;

public class FiltroDocenteDTO implements Serializable {

	private static final long serialVersionUID = -1067303712899236108L;

	private String nombre;

	private String numeroIdentificacion;
	
	private String codigo;
	
	private EstadoDocenteEnum estado;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroDocenteDTO() {
		//Constructor sin parámetros
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
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

	public EstadoDocenteEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoDocenteEnum estado) {
		this.estado = estado;
	}
}