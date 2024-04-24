package co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FACULTAD")
public class FacultadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FACULTAD", nullable = false)
	private Long idFacultad;

	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "ABREVIATURA")
	private String abreviatura;

	public FacultadEntity() {
	}

	public FacultadEntity(String nombre) {
		super();
		this.nombre = nombre;
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

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
}
