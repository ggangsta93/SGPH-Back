package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

public class FiltroEspacioFisicoDTO implements Serializable {

	private static final long serialVersionUID = -147848070840420608L;

	private List<Long> listaIdFacultad;

	private List<Long> listaIdEdificio;

	private List<Long> listaIdTipoEspacioFisico;

	private String numeroEspacioFisico;

	private Boolean estado;

	private Long capacidad;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroEspacioFisicoDTO() {
		super();
	}

	public List<Long> getListaIdFacultad() {
		return listaIdFacultad;
	}

	public void setListaIdFacultad(List<Long> listaIdFacultad) {
		this.listaIdFacultad = listaIdFacultad;
	}

	public List<Long> getListaIdEdificio() {
		return listaIdEdificio;
	}

	public void setListaIdEdificio(List<Long> listaIdEdificio) {
		this.listaIdEdificio = listaIdEdificio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
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