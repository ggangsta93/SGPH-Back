package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "MUNICIPIO", uniqueConstraints = { @UniqueConstraint(columnNames = { "nombre" }) })
public class MunicipioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_MUNICIPIO", nullable = false)
	private Long idMunicipio;

	@Column(name = "NOMBRE", nullable = false)
	private String nombre;

	public Long getIdMunicipio() {
		return idMunicipio;
	}
	
	public void setIdMunicipio(Long idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}