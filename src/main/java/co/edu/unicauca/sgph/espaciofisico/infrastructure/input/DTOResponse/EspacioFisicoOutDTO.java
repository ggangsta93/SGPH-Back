package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class EspacioFisicoOutDTO {

	private Long idEspacioFisico;

	private String OID;

	private Long capacidad;

	private EstadoEspacioFisicoEnum estado;

	private String salon;

	private Long idEdificio;

	private Long idUbicacion;

	private Long idTipoEspacioFisico;

	private List<AgrupadorEspacioFisicoDTO> lstIdAgrupadorEspacioFisico;

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	public Long getIdTipoEspacioFisico() {
		return idTipoEspacioFisico;
	}

	public void setIdTipoEspacioFisico(Long idTipoEspacioFisico) {
		this.idTipoEspacioFisico = idTipoEspacioFisico;
	}

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public EstadoEspacioFisicoEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoEspacioFisicoEnum estado) {
		this.estado = estado;
	}

	public List<AgrupadorEspacioFisicoDTO> getLstIdAgrupadorEspacioFisico() {
		return lstIdAgrupadorEspacioFisico;
	}

	public void setLstIdAgrupadorEspacioFisico(List<AgrupadorEspacioFisicoDTO> lstIdAgrupadorEspacioFisico) {
		this.lstIdAgrupadorEspacioFisico = lstIdAgrupadorEspacioFisico;
	}

	public String getOID() {
		return OID;
	}

	public void setOID(String oID) {
		OID = oID;
	}

	public Long getIdEdificio() {
		return idEdificio;
	}

	public void setIdEdificio(Long idEdificio) {
		this.idEdificio = idEdificio;
	}

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

}