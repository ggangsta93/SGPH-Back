package co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;

@Entity
@Table(name = "EDIFICIO")
public class EdificioEntity {

	@Id
	@SequenceGenerator(name = "SEC_EDIFICIO_GENERATOR", sequenceName = "SEC_EDIFICIO", allocationSize = 1, initialValue = 9)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_EDIFICIO_GENERATOR")
	@Column(name = "ID_EDIFICIO", nullable = false)
	private Long idEdificio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_FACULTAD")
	private FacultadEntity facultad;
	
	@Column(name = "NOMBRE")
	private String nombre;

	public EdificioEntity() {
	}

	public EdificioEntity(String nombre, FacultadEntity facultad) {
		this.nombre = nombre;
		this.facultad = facultad;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public FacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadEntity facultad) {
		this.facultad = facultad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
