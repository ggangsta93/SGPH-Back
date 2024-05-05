package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class EspacioFisicoDTO {

	private Long idEspacioFisico;
	private Long capacidad;
	private EstadoEspacioFisicoEnum estado;
	private String salon;
	private String edificio;
	private String ubicacion;
	private String tipoEspacioFisico;

	public EspacioFisicoDTO() {
		// Cosntructor sin parámetros
	}

	public EspacioFisicoDTO(String ubicacion, String edificio, String tipoEspacioFisico, String salon,
			Long capacidad, EstadoEspacioFisicoEnum estado, Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
		this.capacidad = capacidad;
		this.estado = estado;
		this.salon = salon;
		this.edificio = edificio;
		this.ubicacion = ubicacion;
		this.tipoEspacioFisico = tipoEspacioFisico;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public EstadoEspacioFisicoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEspacioFisicoEnum estado) {
		this.estado = estado;
	}

	public String getTipoEspacioFisico() {
		return tipoEspacioFisico;
	}

	public void setTipoEspacioFisico(String tipoEspacioFisico) {
		this.tipoEspacioFisico = tipoEspacioFisico;
	}
	
	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}
}