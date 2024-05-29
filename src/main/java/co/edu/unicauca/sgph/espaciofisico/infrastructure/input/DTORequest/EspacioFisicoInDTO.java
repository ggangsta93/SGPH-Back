package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class EspacioFisicoInDTO {

	private Long idEspacioFisico;

	private String OID;

	private Long capacidad;

	private EstadoEspacioFisicoEnum estado;

	private String salon;

	private Long idEdificio;

	private Long idUbicacion;
	
	private Long idTipoEspacioFisico;

	private List<Long> lstIdAgrupadorEspacioFisico;
	private List<Long> recursos;

	private List<Long> saveIdAgrupadores;

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
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

	public List<Long> getLstIdAgrupadorEspacioFisico() {
		return lstIdAgrupadorEspacioFisico;
	}

	public void setLstIdAgrupadorEspacioFisico(List<Long> lstIdAgrupadorEspacioFisico) {
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

	public List<Long> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Long> recursos) {
		this.recursos = recursos;
	}

	public List<Long> getSaveIdAgrupadores() {
		return saveIdAgrupadores;
	}

	public void setSaveIdAgrupadores(List<Long> saveIdAgrupadores) {
		this.saveIdAgrupadores = saveIdAgrupadores;
	}
}