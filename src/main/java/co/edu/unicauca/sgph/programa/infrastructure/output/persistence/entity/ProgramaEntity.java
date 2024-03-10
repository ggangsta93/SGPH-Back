package co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;

@Entity
@Table(name = "PROGRAMA")
public class ProgramaEntity {

	@Id
	@SequenceGenerator(name = "SEC_PROGRAMA_GENERATOR", sequenceName = "SEC_PROGRAMA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_PROGRAMA_GENERATOR")
	@Column(name = "ID_PROGRAMA", nullable = false)
	private Long idPrograma;

	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "ABREVIATURA")
	private String abreviatura;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_FACULTAD")
	private FacultadEntity facultad;

	@OneToMany(mappedBy = "programa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AsignaturaEntity> asignaturas;
	
	public ProgramaEntity() {
	}
	
	public ProgramaEntity(String nombre, FacultadEntity facultad) {
		this.nombre = nombre;
		this.facultad = facultad;
	}
	
	public Long getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Long idPrograma) {
		this.idPrograma = idPrograma;
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

	public FacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadEntity facultad) {
		this.facultad = facultad;
	}

	public List<AsignaturaEntity> getAsignaturas() {
		return asignaturas;
	}

	public void setAsignaturas(List<AsignaturaEntity> asignaturas) {
		this.asignaturas = asignaturas;
	}
}