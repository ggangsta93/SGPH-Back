package co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FACULTAD")
public class FacultadEntity {

	@Id
	@SequenceGenerator(name = "SEC_FACULTAD_GENERATOR", sequenceName = "SEC_FACULTAD", allocationSize = 1, initialValue = 10)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_FACULTAD_GENERATOR")
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
