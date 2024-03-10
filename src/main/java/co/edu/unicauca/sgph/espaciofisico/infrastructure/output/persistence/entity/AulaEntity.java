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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.entity.EdificioEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

@Entity
@Table(name = "AULA")
public class AulaEntity {

	@Id
	@SequenceGenerator(name = "SEC_AULA_GENERATOR", sequenceName = "SEC_AULA", allocationSize = 1, initialValue = 904)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEC_AULA_GENERATOR")
	@Column(name = "ID_AULA", nullable = false)
	private Long idAula;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_AULA")
	private TipoAulaEntity tipoAula;

	@Column(name = "NUMERO_AULA")
	private String numeroAula;

	@Column(name = "CAPACIDAD")
	private Long capacidad;
	
	@Column(name = "ESTADO")
	private Boolean estado;

	@ManyToMany(mappedBy = "aulas")
	private List<HorarioEntity> horarios;

	@ManyToOne
	@JoinColumn(name = "ID_EDIFICIO")
	private EdificioEntity edificio;

	@OneToMany(mappedBy = "aula")
	private List<RecursoAulaEntity> recursosAula; 
	
	public AulaEntity() {
        // Constructor sin argumentos requerido por ModelMapper y JPA
    }

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}

	public TipoAulaEntity getTipoAula() {
		return tipoAula;
	}

	public void setTipoAula(TipoAulaEntity tipoAula) {
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

	public List<RecursoAulaEntity> getRecursosAula() {
		return recursosAula;
	}

	public void setRecursosAula(List<RecursoAulaEntity> recursosAula) {
		this.recursosAula = recursosAula;
	}
	
}
