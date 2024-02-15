package co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIPO_IDENTIFICACION")
public class TipoIdentificacionEntity {

	@Id
	@Column(name = "ID_TIPO_IDENTIFICACION")
	private Long idTipoIdentificacion;

	@Column(name = "CODIGO_TIPO_IDENTIFICACION")
	private String codigoTipoIdentificacion;

	@Column(name = "TIPO_IDENTIFICACION")
	private String tipoIdentificacion;

	public TipoIdentificacionEntity() {
	}
	
	public TipoIdentificacionEntity(Long idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public Long getIdTipoIdentificacion() {
		return idTipoIdentificacion;
	}

	public void setIdTipoIdentificacion(Long idTipoIdentificacion) {
		this.idTipoIdentificacion = idTipoIdentificacion;
	}

	public String getCodigoTipoIdentificacion() {
		return codigoTipoIdentificacion;
	}

	public void setCodigoTipoIdentificacion(String codigoTipoIdentificacion) {
		this.codigoTipoIdentificacion = codigoTipoIdentificacion;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
}
