package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;

@Entity
@Table(name = "ESPACIO_FISICO")
public class EspacioFisicoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ESPACIO_FISICO", nullable = false)
	private Long idEspacioFisico;
	
	@Column(name = "OID")
	private String OID;
	
	@Column(name = "CAPACIDAD")
	private Long capacidad;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO")
	private EstadoEspacioFisicoEnum estado;

	@Column(name = "SALON")
	private String salon;
			
	@ManyToOne
	@JoinColumn(name = "ID_TIPO_ESPACIO_FISICO")
	private TipoEspacioFisicoEntity tipoEspacioFisico;

	@ManyToMany(mappedBy = "espaciosFisicos")
	private List<HorarioEntity> horarios;

	@OneToMany(mappedBy = "espacioFisico")
	private List<RecursoEspacioFisicoEntity> recursosEspacioFisico;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ESPACIOFISICO_AGRUPADOR_ESP_FIS", joinColumns = @JoinColumn(name = "ID_ESPACIO_FISICO"), inverseJoinColumns = @JoinColumn(name = "ID_AGRUPADOR_ESPACIO_FISICO"))
	private List<AgrupadorEspacioFisicoEntity> agrupadores;
		
	@ManyToOne
	@JoinColumn(name = "ID_UBICACION")
	private UbicacionEntity ubicacion;
	
	@ManyToOne
	@JoinColumn(name = "ID_EDIFICIO")
	private EdificioEntity edificio;	
	
	/**
	 * Constructor de la clase para el método:
	 * -consultarCapacidadYSalonPorListaIdEspacioFisico
	 * 
	 * @param idEspacioFisico
	 * @param capacidad
	 * @param salon
	 * @param estado
	 */
	public EspacioFisicoEntity(Long idEspacioFisico, Long capacidad, String salon, EstadoEspacioFisicoEnum estado) {
		this.idEspacioFisico = idEspacioFisico;
		this.capacidad = capacidad;
		this.salon = salon;
		this.estado = estado;
	}

	/**
	 * Constructor de la clase sin parámetros
	 */
	public EspacioFisicoEntity() {
		this.agrupadores=new ArrayList<>();
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

	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
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

	public UbicacionEntity getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionEntity ubicacion) {
		this.ubicacion = ubicacion;
	}

	public EdificioEntity getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioEntity edificio) {
		this.edificio = edificio;
	}

	public List<AgrupadorEspacioFisicoEntity> getAgrupadores() {
		return agrupadores;
	}

	public void setAgrupadores(List<AgrupadorEspacioFisicoEntity> agrupadores) {
		this.agrupadores = agrupadores;
	}

	public String getOID() {
		return OID;
	}

	public void setOID(String oID) {
		OID = oID;
	}	
}