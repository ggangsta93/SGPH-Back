package co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;

public class AsignacionEspacioFisicoDTO {

	private Long idGrupo;
	private List<EspacioFisicoDTO> quitados;
	private List<EspacioFisicoDTO> agregados;

	public Long getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Long idGrupo) {
		this.idGrupo = idGrupo;
	}

	public List<EspacioFisicoDTO> getQuitados() {
		return quitados;
	}

	public void setQuitados(List<EspacioFisicoDTO> quitados) {
		this.quitados = quitados;
	}

	public List<EspacioFisicoDTO> getAgregados() {
		return agregados;
	}

	public void setAgregados(List<EspacioFisicoDTO> agregador) {
		this.agregados = agregador;
	}
}