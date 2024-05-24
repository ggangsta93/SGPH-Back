package co.edu.unicauca.sgph.espaciofisico.domain.model;

public class Municipio {

	private Long idMunicipio;

	private String nombre;

	public Municipio() {
		// Constructor sin parámetros
	}

	public Municipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Long getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}