package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

import java.io.Serializable;

public class FiltroEspacioFisicoAgrupadorDTO implements Serializable {

	private static final long serialVersionUID = -147848070840420608L;

	private String ubicacion;
	private String nombre;
	private String tipo;

	private Long idAgrupador;
	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdAgrupador() {
		return idAgrupador;
	}

	public void setIdAgrupador(Long idAgrupador) {
		this.idAgrupador = idAgrupador;
	}
}