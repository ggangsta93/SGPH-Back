package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class MunicipioOutDTO {

	private Long idMunicipio;

	private String nombre;

	public MunicipioOutDTO() {
		// Constructor sin parámetros
	}

	public MunicipioOutDTO(Long idMunicipio) {
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