package co.edu.unicauca.sgph.aula.domain.model;

public class TipoAula {

	private Long idTipoAula;

	private String tipo;

	private String observacion;
	
	public TipoAula() {
		//Constructor sin parámetros
	}

	public TipoAula(Long idTipoAula) {
		this.idTipoAula = idTipoAula;
	}

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