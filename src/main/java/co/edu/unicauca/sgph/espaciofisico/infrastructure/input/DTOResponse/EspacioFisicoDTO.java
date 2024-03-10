package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class EspacioFisicoDTO {

	private String abreviaturaFacultad;
	private String nombreEdificio;
	private String tipoEspacioFisico;
	private String numeroEspacioFisico;
	private Long capacidad;
	private Boolean estado;
	private Long idEspacioFisico;
	
	public EspacioFisicoDTO() {
		//Cosntructor sin parámetros
	}
	
	public EspacioFisicoDTO(String abreviaturaFacultad, String nombreEdificio, String tipoEspacioFisico, String numeroEspacioFisico,
			Long capacidad, Boolean estado, Long idEspacioFisico) {
		this.abreviaturaFacultad = abreviaturaFacultad;
		this.nombreEdificio = nombreEdificio;
		this.tipoEspacioFisico = tipoEspacioFisico;
		this.numeroEspacioFisico = numeroEspacioFisico;
		this.capacidad = capacidad;
		this.estado = estado;
		this.idEspacioFisico=idEspacioFisico;
	}
	public String getAbreviaturaFacultad() {
		return abreviaturaFacultad;
	}
	public void setAbreviaturaFacultad(String abreviaturaFacultad) {
		this.abreviaturaFacultad = abreviaturaFacultad;
	}
	public String getNombreEdificio() {
		return nombreEdificio;
	}
	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
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

	public String getTipoEspacioFisico() {
		return tipoEspacioFisico;
	}

	public void setTipoEspacioFisico(String tipoEspacioFisico) {
		this.tipoEspacioFisico = tipoEspacioFisico;
	}

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public void setNumeroEspacioFisico(String numeroEspacioFisico) {
		this.numeroEspacioFisico = numeroEspacioFisico;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}
}