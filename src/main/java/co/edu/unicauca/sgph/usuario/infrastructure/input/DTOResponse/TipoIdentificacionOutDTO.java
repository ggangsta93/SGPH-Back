package co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse;

public class TipoIdentificacionOutDTO {

	private Long idTipoIdentificacion;

	private String codigoTipoIdentificacion;

	private String tipoIdentificacion;
	
	public TipoIdentificacionOutDTO() {
		//Constructor sin argumentos
	}
		
	public TipoIdentificacionOutDTO(Long idTipoIdentificacion) {
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