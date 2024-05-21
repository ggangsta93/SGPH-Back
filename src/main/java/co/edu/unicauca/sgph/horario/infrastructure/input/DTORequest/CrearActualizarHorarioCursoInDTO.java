package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

import java.util.List;

public class CrearActualizarHorarioCursoInDTO {

	private Long idCurso;

	private List<FranjaHorariaCursoAsociarInDTO> listaFranjaHorariaCursoAsociarInDTO;

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}
	
	public List<FranjaHorariaCursoAsociarInDTO> getListaFranjaHorariaCursoAsociarInDTO() {
		return listaFranjaHorariaCursoAsociarInDTO;
	}

	public void setListaFranjaHorariaCursoAsociarInDTO(
			List<FranjaHorariaCursoAsociarInDTO> listaFranjaHorariaCursoAsociarInDTO) {
		this.listaFranjaHorariaCursoAsociarInDTO = listaFranjaHorariaCursoAsociarInDTO;
	}
}