package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest;

import java.io.Serializable;
import java.util.List;

public class GenerarHorarioBaseInDTO implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = 8733120435630482471L;

	private Long idPrograma;

	private Long idPeriodoAcademicoBase;
	
	private List<Long> lstIdAsignaturaExcluir;

	public GenerarHorarioBaseInDTO() {
	}

	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Long getIdPeriodoAcademicoBase() {
		return idPeriodoAcademicoBase;
	}

	public void setIdPeriodoAcademicoBase(Long idPeriodoAcademicoBase) {
		this.idPeriodoAcademicoBase = idPeriodoAcademicoBase;
	}

	public List<Long> getLstIdAsignaturaExcluir() {
		return lstIdAsignaturaExcluir;
	}

	public void setLstIdAsignaturaExcluir(List<Long> lstIdAsignaturaExcluir) {
		this.lstIdAsignaturaExcluir = lstIdAsignaturaExcluir;
	}
}