package co.edu.unicauca.sgph.espaciofisico.domain.model;

import java.util.List;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public class Aula {

	private Long idAula;

	private TipoAula tipoAula;

	private String numeroAula;

	private Long capacidad;
	
	private Boolean estado;

	private List<Horario> horarios;

	private Edificio edificio;

	private List<RecursoAula> recursosAula;
	
	public Aula() {
		//Constructor sin argumentos
	}
	
	public Aula(Long idAula) {
		this.idAula = idAula;
	}

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}

	public TipoAula getTipoAula() {
		return tipoAula;
	}

	public void setTipoAula(TipoAula tipoAula) {
		this.tipoAula = tipoAula;
	}

	public String getNumeroAula() {
		return numeroAula;
	}

	public void setNumeroAula(String numeroAula) {
		this.numeroAula = numeroAula;
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

	public List<RecursoAula> getRecursosAula() {
		return recursosAula;
	}

	public void setRecursosAula(List<RecursoAula> recursosAula) {
		this.recursosAula = recursosAula;
	}	
}