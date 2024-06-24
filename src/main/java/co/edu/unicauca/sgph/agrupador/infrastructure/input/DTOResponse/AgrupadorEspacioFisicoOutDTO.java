package co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse;

public class AgrupadorEspacioFisicoOutDTO {

	private Long idAgrupadorEspacioFisico;

	private String nombre;

	private String observacion;

	private Long idFacultad;
	
	private String nombreFacultad;
	
	private Long cantidadEspaciosFisicosAsignados;

	public AgrupadorEspacioFisicoOutDTO() {
	}

	public AgrupadorEspacioFisicoOutDTO(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

	public Long getIdAgrupadorEspacioFisico() {
		return idAgrupadorEspacioFisico;
	}

	public void setIdAgrupadorEspacioFisico(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getNombreFacultad() {
		return nombreFacultad;
	}

	public void setNombreFacultad(String nombreFacultad) {
		this.nombreFacultad = nombreFacultad;
	}

	public Long getCantidadEspaciosFisicosAsignados() {
		return cantidadEspaciosFisicosAsignados;
	}

	public void setCantidadEspaciosFisicosAsignados(Long cantidadEspaciosFisicosAsignados) {
		this.cantidadEspaciosFisicosAsignados = cantidadEspaciosFisicosAsignados;
	}
}