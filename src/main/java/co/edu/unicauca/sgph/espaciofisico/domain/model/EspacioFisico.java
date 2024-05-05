package co.edu.unicauca.sgph.espaciofisico.domain.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public class EspacioFisico {

	private Long idEspacioFisico;
	
	private String OID;

	private Long capacidad;

	private EstadoEspacioFisicoEnum estado;

	private String salon;

	private String edificio;

	private String ubicacion;

	private TipoEspacioFisico tipoEspacioFisico;

	private List<Horario> horarios;

	private List<RecursoEspacioFisico> recursosEspacioFisico;

	private List<AgrupadorEspacioFisico> agrupadores;
	
	private String municipio;

	public EspacioFisico() {
		agrupadores = new ArrayList<>();
	}

	public EspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public TipoEspacioFisico getTipoEspacioFisico() {
		return tipoEspacioFisico;
	}

	public void setTipoEspacioFisico(TipoEspacioFisico tipoEspacioFisico) {
		this.tipoEspacioFisico = tipoEspacioFisico;
	}

	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}

	public List<RecursoEspacioFisico> getRecursosEspacioFisico() {
		return recursosEspacioFisico;
	}

	public void setRecursosEspacioFisico(List<RecursoEspacioFisico> recursosEspacioFisico) {
		this.recursosEspacioFisico = recursosEspacioFisico;
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

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	public String getEdificio() {
		return edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public List<AgrupadorEspacioFisico> getAgrupadores() {
		return agrupadores;
	}

	public void setAgrupadores(List<AgrupadorEspacioFisico> agrupadores) {
		this.agrupadores = agrupadores;
	}

	public String getOID() {
		return OID;
	}

	public void setOID(String oID) {
		OID = oID;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
}