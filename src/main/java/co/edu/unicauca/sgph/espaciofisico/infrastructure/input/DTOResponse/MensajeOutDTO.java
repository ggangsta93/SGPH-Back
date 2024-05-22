package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class MensajeOutDTO {
	private String descripcion;
	private Boolean error;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}
}