package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class UbicacionOutDTO {

	private Long idUbicacion;

	private String nombre;

	private String descripcion;

	private Long idMunicipio;

	public UbicacionOutDTO() {
		// Constructor sin parámetros
	}

	public UbicacionOutDTO(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
}