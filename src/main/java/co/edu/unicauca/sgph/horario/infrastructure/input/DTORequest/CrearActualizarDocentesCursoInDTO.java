package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

import java.util.List;

public class CrearActualizarDocentesCursoInDTO {

	private Long idCurso;

	private List<Long> listaIdPersona;

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public List<Long> getListaIdPersona() {
		return listaIdPersona;
	}

	public void setListaIdPersona(List<Long> listaIdPersona) {
		this.listaIdPersona = listaIdPersona;
	}
}