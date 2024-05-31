package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest;

import java.io.Serializable;

public class FiltroPeriodoAcademicoDTO implements Serializable {
	
	/**
	 * Atributo que determina 
	 */
	private static final long serialVersionUID = 1329435651783952736L;

	private Integer pagina;

	private Integer registrosPorPagina;

	public FiltroPeriodoAcademicoDTO() {
		//Constructor sin parámetros
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

}