package co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

public class FiltroGrupoDTO implements Serializable {

	private static final long serialVersionUID = -147848070840420608L;

	private List<Long> listaIdFacultades;
	private String nombre;
	private Integer pageNumber;
	private Integer pageSize;

	public List<Long> getListaIdFacultades() {
		return listaIdFacultades;
	}

	public void setListaIdFacultades(List<Long> listaIdFacultades) {
		this.listaIdFacultades = listaIdFacultades;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}