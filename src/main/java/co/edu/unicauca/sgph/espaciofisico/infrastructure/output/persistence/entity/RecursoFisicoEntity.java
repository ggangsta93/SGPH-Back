package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECURSO_FISICO")
public class RecursoFisicoEntity {

	@Id
	@Column(name = "ID_RECURSO_FISICO")
	private Long idRecursoFisico;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DESCRIPCION")
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
