package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

public class TipoEspacioFisicoInDTO {
	
	private Long idTipoEspacioFisico;

	private String tipo;

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