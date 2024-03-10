package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class AulaOutDTO {

	private Long idAula;

	private Long capacidad;
	
	private Boolean estado;
	
	private String numeroAula;
	
	private Long idEdificio;
	
	private Long idTipoAula;

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
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

	public String getNumeroAula() {
		return numeroAula;
	}

	public void setNumeroAula(String numeroAula) {
		this.numeroAula = numeroAula;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdTipoAula() {
		return idTipoAula;
	}

	public void setIdTipoAula(Long idTipoAula) {
		this.idTipoAula = idTipoAula;
	}	
}