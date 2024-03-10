package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TIPO_AULA", uniqueConstraints = { @UniqueConstraint(columnNames = { "tipo"}) })
public class TipoAulaEntity {
	@Id
	@SequenceGenerator(name = "SEC_TIPO_AULA_GENERATOR", sequenceName = "SEC_TIPO_AULA", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_TIPO_AULA_GENERATOR")
	@Column(name = "ID_TIPO_AULA", nullable = false)
	private Long idTipoAula;

	@Column(name = "TIPO", nullable = false)
	private String tipo;

	@Column(name = "OBSERVACION", nullable = true)
	private String observacion;

	public Long getIdTipoAula() {
		return idTipoAula;
	}

	public void setIdTipoAula(Long idTipoAula) {
		this.idTipoAula = idTipoAula;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
}