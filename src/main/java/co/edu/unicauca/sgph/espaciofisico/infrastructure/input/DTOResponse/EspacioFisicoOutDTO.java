package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;

public class EspacioFisicoOutDTO {

	private Long idEspacioFisico;

	private String nombreTipoEspacioFisico;
	private String OID;

	private Long capacidad;

	private EstadoEspacioFisicoEnum estado;

	private String salon;

	private Long idEdificio;
	private String nombreEdificio;

	private Long idUbicacion;
	private String nombreUbicacion;

	private Long idTipoEspacioFisico;

	private List<AgrupadorEspacioFisicoDTO> lstIdAgrupadorEspacioFisico;
	private List<RecursoOutDTO> recursos;

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

	public String getNombreEdificio() {
		return nombreEdificio;
	}

	public void setNombreEdificio(String nombreEdificio) {
		this.nombreEdificio = nombreEdificio;
	}

	public String getNombreUbicacion() {
		return nombreUbicacion;
	}

	public void setNombreUbicacion(String nombreUbicacion) {
		this.nombreUbicacion = nombreUbicacion;
	}

	public String getNombreTipoEspacioFisico() {
		return nombreTipoEspacioFisico;
	}

	public void setNombreTipoEspacioFisico(String nombreTipoEspacioFisico) {
		this.nombreTipoEspacioFisico = nombreTipoEspacioFisico;
	}

	public List<RecursoOutDTO> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<RecursoOutDTO> recursos) {
		this.recursos = recursos;
	}
}