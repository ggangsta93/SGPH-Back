package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.entity.EdificioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

@Entity
@Table(name = "ESPACIO_FISICO")
public class EspacioFisicoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESPACIO_FISICO", nullable = false)
	private Long idEspacioFisico;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ESPACIO_FISICO")
	private TipoEspacioFisicoEntity tipoEspacioFisico;

	@Column(name = "NUMERO_ESPACIO_FISICO")
	private String numeroEspacioFisico;

	@Column(name = "CAPACIDAD")
	private Long capacidad;
	
	@Column(name = "ESTADO")
	private Boolean estado;

	@ManyToMany(mappedBy = "espaciosFisicos")
	private List<HorarioEntity> horarios;

	@ManyToOne
	@JoinColumn(name = "ID_EDIFICIO")
	private EdificioEntity edificio;

	@OneToMany(mappedBy = "espacioFisico")
	private List<RecursoEspacioFisicoEntity> recursosEspacioFisico; 
	
	public EspacioFisicoEntity() {
        // Constructor sin argumentos requerido por ModelMapper y JPA
    }

	public Long getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}

	public EdificioEntity getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioEntity edificio) {
		this.edificio = edificio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public TipoEspacioFisicoEntity getTipoEspacioFisico() {
		return tipoEspacioFisico;
	}

	public void setTipoEspacioFisico(TipoEspacioFisicoEntity tipoEspacioFisico) {
		this.tipoEspacioFisico = tipoEspacioFisico;
	}

	public String getNumeroEspacioFisico() {
		return numeroEspacioFisico;
	}

	public void setNumeroEspacioFisico(String numeroEspacioFisico) {
		this.numeroEspacioFisico = numeroEspacioFisico;
	}

	public List<HorarioEntity> getHorarios() {
		return horarios;
	}

	public void setHorarios(List<HorarioEntity> horarios) {
		this.horarios = horarios;
	}

	public List<RecursoEspacioFisicoEntity> getRecursosEspacioFisico() {
		return recursosEspacioFisico;
	}

	public void setRecursosEspacioFisico(List<RecursoEspacioFisicoEntity> recursosEspacioFisico) {
		this.recursosEspacioFisico = recursosEspacioFisico;
	}	
}