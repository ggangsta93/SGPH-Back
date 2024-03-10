package co.edu.unicauca.sgph.espaciofisico.domain.model;

public class RecursoFisico {

	private Long idRecursoFisico;

	private String nombre;

	private String descripcion;

	public Long getIdRecursoFisico() {
		return idRecursoFisico;
	}

	public void setIdRecursoFisico(Long idRecursoFisico) {
		this.idRecursoFisico = idRecursoFisico;
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