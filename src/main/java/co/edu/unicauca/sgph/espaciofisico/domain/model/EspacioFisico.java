package co.edu.unicauca.sgph.espaciofisico.domain.model;

import java.util.List;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public class EspacioFisico {

	private Long idEspacioFisico;

	private TipoEspacioFisico tipoEspacioFisico;

	private String numeroEspacioFisico;

	private Long capacidad;
	
	private Boolean estado;

	private List<Horario> horarios;

	private Edificio edificio;

	private List<RecursoEspacioFisico> recursosEspacioFisico;
	
	public EspacioFisico() {
		//Constructor sin argumentos
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

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public void setNumeroEspacioFisico(String numeroEspacioFisico) {
		this.numeroEspacioFisico = numeroEspacioFisico;
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

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<Horario> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}	
}