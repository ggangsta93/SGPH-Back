package co.edu.unicauca.sgph.espaciofisico.domain.model;

public class Ubicacion {

	private Long idUbicacion;

	private String nombre;

	private String descripcion;

	private Municipio municipio;

	public Ubicacion() {
		// Constructor sin parámetros
	}

	public Ubicacion(Long idUbicacion) {
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

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}
}