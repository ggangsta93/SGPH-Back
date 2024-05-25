package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class EspacioFisicoDTO {

	private Long idEspacioFisico;
	private Long capacidad;
	private EstadoEspacioFisicoEnum estado;
	private String salon;
	private Long idEdificio;
	private String nombreEdificio;
	private Long idUbicacion;
	private String nombreUbicacion;
	private String tipoEspacioFisico;

	public EspacioFisicoDTO() {
		// Cosntructor sin parámetros
	}

	public EspacioFisicoDTO(Long idUbicacion, Long idEdificio, String tipoEspacioFisico, String salon, Long capacidad,
			EstadoEspacioFisicoEnum estado, Long idEspacioFisico, String nombreUbicacion, String nombreEdificio) {
		this.idEspacioFisico = idEspacioFisico;
		this.capacidad = capacidad;
		this.estado = estado;
		this.salon = salon;
		this.idEdificio = idEdificio;
		this.idUbicacion = idUbicacion;
		this.tipoEspacioFisico = tipoEspacioFisico;
		this.nombreEdificio=nombreEdificio;
		this.nombreUbicacion=nombreUbicacion;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
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

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public String getNombreUbicacion() {
		return nombreUbicacion;
	}

	public void setNombreUbicacion(String nombreUbicacion) {
		this.nombreUbicacion = nombreUbicacion;
	}
}