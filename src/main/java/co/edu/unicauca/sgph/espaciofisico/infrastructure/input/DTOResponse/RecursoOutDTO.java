package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

public class RecursoOutDTO {
	private Long idRecursoEspacioFisico;
	private Long idRecurso;
	private String nombre;
	private String descripcion;
	private Long cantidad;

	public RecursoOutDTO(Long idRecurso, String nombre, String descripcion) {
		this.idRecurso = idRecurso;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
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

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdRecursoEspacioFisico() {
		return idRecursoEspacioFisico;
	}

	public void setIdRecursoEspacioFisico(Long idRecursoEspacioFisico) {
		this.idRecursoEspacioFisico = idRecursoEspacioFisico;
	}
}