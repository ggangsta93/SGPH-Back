package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

public class FormatoPresentacionFranjaHorariaCursoDTO {
	
	private Long idAula;

	private String nombreCompletoAula;
	
	public FormatoPresentacionFranjaHorariaCursoDTO() {
		//Constructor sin parametros
	}

	public FormatoPresentacionFranjaHorariaCursoDTO(Long idAula, String nombreCompletoAula) {
		this.idAula = idAula;
		this.nombreCompletoAula = nombreCompletoAula;
	}

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}

	public String getNombreCompletoAula() {
		return nombreCompletoAula;
	}

	public void setNombreCompletoAula(String nombreCompletoAula) {
		this.nombreCompletoAula = nombreCompletoAula;
	}
}