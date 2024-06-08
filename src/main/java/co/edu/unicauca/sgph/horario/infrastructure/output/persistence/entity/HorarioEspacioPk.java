package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;

@Embeddable
public class HorarioEspacioPk implements Serializable {

	/**
	 * Atributo que determina 
	 */
	private static final long serialVersionUID = -6657025402219941644L;

	@Column(name = "ID_HORARIO")
	private HorarioEntity horarioId;

	@Column(name = "ID_ESPACIO_FISICO")
	private EspacioFisicoEntity espaciosFisicoId;

	public HorarioEntity getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(HorarioEntity horarioId) {
		this.horarioId = horarioId;
	}

	public EspacioFisicoEntity getEspaciosFisicoId() {
		return espaciosFisicoId;
	}

	public void setEspaciosFisicoId(EspacioFisicoEntity espaciosFisicoId) {
		this.espaciosFisicoId = espaciosFisicoId;
	}
}