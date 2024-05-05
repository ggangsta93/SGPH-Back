package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;

@Entity
@Table(name = "AGRUPADOR_ESPACIO_FISICO", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }) })
public class AgrupadorEspacioFisicoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_AGRUPADOR_ESPACIO_FISICO", nullable = false)
	private Long idAgrupadorEspacioFisico;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	@Column(name = "OBSERVACION", nullable = true)
	private String observacion;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_FACULTAD")
	private FacultadEntity facultad;

	public Long getIdAgrupadorEspacioFisico() {
		return idAgrupadorEspacioFisico;
	}

	public void setIdAgrupadorEspacioFisico(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public FacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadEntity facultad) {
		this.facultad = facultad;
	}
}