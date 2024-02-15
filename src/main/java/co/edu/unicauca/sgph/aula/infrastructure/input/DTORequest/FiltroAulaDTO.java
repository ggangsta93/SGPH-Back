package co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

public class FiltroAulaDTO implements Serializable {

	private static final long serialVersionUID = -147848070840420608L;

	private List<Long> listaIdFacultad;

	private List<Long> listaIdEdificio;

	private List<Long> listaIdTipoAula;

	private String numeroAula;

	private Boolean estado;
	
	private Long capacidad;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroAulaDTO() {
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

	public List<Long> getListaIdTipoAula() {
		return listaIdTipoAula;
	}

	public void setListaIdTipoAula(List<Long> listaIdTipoAula) {
		this.listaIdTipoAula = listaIdTipoAula;
	}

	public String getNumeroAula() {
		return numeroAula;
	}

	public void setNumeroAula(String numeroAula) {
		this.numeroAula = numeroAula;
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
}