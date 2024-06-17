package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HorarioEspacioPk implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = -6657025402219941644L;

	@Column(name = "ID_HORARIO")
	private Long idHorario;

	@Column(name = "ID_ESPACIO_FISICO")
	private Long idEspacioFisico;

	public HorarioEspacioPk() {
	}

	public HorarioEspacioPk(Long idHorario, Long idEspacioFisico) {
		this.idHorario = idHorario;
		this.idEspacioFisico = idEspacioFisico;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}
}