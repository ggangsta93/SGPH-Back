package co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import co.edu.unicauca.sgph.agrupador.infrastructure.input.validation.ExisteNombreAgrupador;

@ExisteNombreAgrupador
public class AgrupadorEspacioFisicoInDTO {

	private Long idAgrupadorEspacioFisico;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String observacion;

	@NotNull
	private Long idFacultad;
	
	/**
	 * Atributo que determina si la invocación es para validar la información o
	 * persistirla
	 */
	private Boolean esValidar;

	public AgrupadorEspacioFisicoInDTO() {
		// Constructor sin parametros
	}

	public AgrupadorEspacioFisicoInDTO(Long idAgrupadorEspacioFisico) {
		this.idAgrupadorEspacioFisico = idAgrupadorEspacioFisico;
	}

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

	public Long getIdFacultad() {
		return idFacultad;
	}

	public void setIdFacultad(Long idFacultad) {
		this.idFacultad = idFacultad;
	}

	public Boolean getEsValidar() {
		return esValidar;
	}

	public void setEsValidar(Boolean esValidar) {
		this.esValidar = esValidar;
	}
}