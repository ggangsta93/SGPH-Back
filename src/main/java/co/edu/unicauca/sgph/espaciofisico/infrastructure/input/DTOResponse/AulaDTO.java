package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class AulaDTO {

	private String abreviaturaFacultad;
	private String nombreEdificio;
	private String tipoAula;
	private String numeroAula;
	private Long capacidad;
	private Boolean estado;
	private Long idAula;
	
	public AulaDTO() {
		//Cosntructor sin parámetros
	}
	
	public AulaDTO(String abreviaturaFacultad, String nombreEdificio, String tipoAula, String numeroAula,
			Long capacidad, Boolean estado, Long idAula) {
		this.abreviaturaFacultad = abreviaturaFacultad;
		this.nombreEdificio = nombreEdificio;
		this.tipoAula = tipoAula;
		this.numeroAula = numeroAula;
		this.capacidad = capacidad;
		this.estado = estado;
		this.idAula=idAula;
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
	public String getTipoAula() {
		return tipoAula;
	}
	public void setTipoAula(String tipoAula) {
		this.tipoAula = tipoAula;
	}
	public String getNumeroAula() {
		return numeroAula;
	}
	public void setNumeroAula(String numeroAula) {
		this.numeroAula = numeroAula;
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
	public Long getIdAula() {
		return idAula;
	}
	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}
}