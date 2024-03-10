package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

public class FormatoPresentacionFranjaHorariaCursoDTO {

	private Long idEspacioFisico;

	private String nombreCompletoEspacioFisico;

	public FormatoPresentacionFranjaHorariaCursoDTO() {
		// Constructor sin parametros
	}

	public FormatoPresentacionFranjaHorariaCursoDTO(Long idEspacioFisico, String nombreCompletoEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
		this.nombreCompletoEspacioFisico = nombreCompletoEspacioFisico;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public String getNombreCompletoEspacioFisico() {
		return nombreCompletoEspacioFisico;
	}

	public void setNombreCompletoEspacioFisico(String nombreCompletoEspacioFisico) {
		this.nombreCompletoEspacioFisico = nombreCompletoEspacioFisico;
	}
}