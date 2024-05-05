package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class FiltroEspacioFisicoDTO implements Serializable {

	private static final long serialVersionUID = -147848070840420608L;

	private List<String> listaUbicacion;

	private List<String> listaEdificio;

	private List<Long> listaIdTipoEspacioFisico;

	private String numeroEspacioFisico;

	private EstadoEspacioFisicoEnum estado;

	private Long capacidad;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroEspacioFisicoDTO() {
		super();
	}

	public List<String> getListaUbicacion() {
		return listaUbicacion;
	}

	public void setListaUbicacion(List<String> listaUbicacion) {
		this.listaUbicacion = listaUbicacion;
	}

	public List<String> getListaEdificio() {
		return listaEdificio;
	}

	public void setListaEdificio(List<String> listaEdificio) {
		this.listaEdificio = listaEdificio;
	}

	public EstadoEspacioFisicoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEspacioFisicoEnum estado) {
		this.estado = estado;
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

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public List<Long> getListaIdTipoEspacioFisico() {
		return listaIdTipoEspacioFisico;
	}

	public void setListaIdTipoEspacioFisico(List<Long> listaIdTipoEspacioFisico) {
		this.listaIdTipoEspacioFisico = listaIdTipoEspacioFisico;
	}

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public void setNumeroEspacioFisico(String numeroEspacioFisico) {
		this.numeroEspacioFisico = numeroEspacioFisico;
	}
}