package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class EspacioFisicoOutDTO {

	private Long idEspacioFisico;

	private Long capacidad;

	private Boolean estado;

	private String numeroEspacioFisico;

	private Long idEdificio;

	private Long idTipoEspacioFisico;

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public void setNumeroEspacioFisico(String numeroEspacioFisico) {
		this.numeroEspacioFisico = numeroEspacioFisico;
	}

	public Long getIdTipoEspacioFisico() {
		return idTipoEspacioFisico;
	}

	public void setIdTipoEspacioFisico(Long idTipoEspacioFisico) {
		this.idTipoEspacioFisico = idTipoEspacioFisico;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}
}