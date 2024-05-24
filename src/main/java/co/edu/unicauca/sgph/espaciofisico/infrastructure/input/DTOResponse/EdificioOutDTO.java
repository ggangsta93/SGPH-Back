package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class EdificioOutDTO {

	private Long idEdificio;

	private String nombre;

	private String descripcion;

	public EdificioOutDTO() {
		// Constructor sin parámetros
	}

	public EdificioOutDTO(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
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
}