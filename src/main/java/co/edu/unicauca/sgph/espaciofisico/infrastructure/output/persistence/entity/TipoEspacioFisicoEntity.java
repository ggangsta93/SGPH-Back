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
@Table(name = "TIPO_ESPACIO_FISICO", uniqueConstraints = { @UniqueConstraint(columnNames = { "tipo"}) })
public class TipoEspacioFisicoEntity {
	@Id
	@SequenceGenerator(name = "SEC_TIPO_ESPACIO_FISICO_GENERATOR", sequenceName = "SEC_TIPO_ESPACIO_FISICO", allocationSize = 1, initialValue = 8)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_TIPO_ESPACIO_FISICO_GENERATOR")
	@Column(name = "ID_TIPO_ESPACIO_FISICO", nullable = false)
	private Long idTipoEspacioFisico;

	@Column(name = "TIPO", nullable = false)
	private String tipo;

	@Column(name = "OBSERVACION", nullable = true)
	private String observacion;

	public Long getIdTipoEspacioFisico() {
		return idTipoEspacioFisico;
	}

	public void setIdTipoEspacioFisico(Long idTipoEspacioFisico) {
		this.idTipoEspacioFisico = idTipoEspacioFisico;
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