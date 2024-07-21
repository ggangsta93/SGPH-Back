package co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity;

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
@Table(name = "DEPARTAMENTO", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"OID"})
})
public class DepartamentoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_DEPARTAMENTO", nullable = false)
	private Long idDepartamento;
	
	@Column(name = "OID")
	private String oid;

	@Column(name = "NOMBRE")
	private String nombre;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_FACULTAD")
	private FacultadEntity facultad;

	public DepartamentoEntity() {

	}

	public DepartamentoEntity(String nombre, FacultadEntity facultad) {
		this.nombre = nombre;
		this.facultad = facultad;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public FacultadEntity getFacultad() {
		return facultad;
	}

	public void setFacultad(FacultadEntity facultad) {
		this.facultad = facultad;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
}