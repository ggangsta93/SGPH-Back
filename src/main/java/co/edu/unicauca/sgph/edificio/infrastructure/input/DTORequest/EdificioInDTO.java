package co.edu.unicauca.sgph.edificio.infrastructure.input.DTORequest;

public class EdificioInDTO {

	private Long idEdificio;

	private Long idFacultad;

	private String nombre;

	public EdificioInDTO() {
		// Constructor sin parámetros
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}