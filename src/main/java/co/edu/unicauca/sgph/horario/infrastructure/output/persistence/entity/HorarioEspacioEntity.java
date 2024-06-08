package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;

@Entity
@Table(name = "HORARIO_ESPACIOFISICO")
public class HorarioEspacioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_HORARIO_ESPACIO", nullable = false)
	private Long idHorarioEspacio;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_HORARIO")
	private HorarioEntity horario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESPACIO_FISICO")
	private EspacioFisicoEntity espaciosFisico;

	public Long getIdHorarioEspacio() {
		return idHorarioEspacio;
	}

	public void setIdHorarioEspacio(Long idHorarioEspacio) {
		this.idHorarioEspacio = idHorarioEspacio;
	}

	public HorarioEntity getHorario() {
		return horario;
	}

	public void setHorario(HorarioEntity horario) {
		this.horario = horario;
	}

	public EspacioFisicoEntity getEspaciosFisico() {
		return espaciosFisico;
	}

	public void setEspaciosFisico(EspacioFisicoEntity espaciosFisico) {
		this.espaciosFisico = espaciosFisico;
	}	
}