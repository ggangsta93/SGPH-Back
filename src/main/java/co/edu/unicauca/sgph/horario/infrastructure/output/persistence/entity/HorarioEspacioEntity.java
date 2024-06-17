package co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;

@Entity
@Table(name = "HORARIO_ESPACIOFISICO")
public class HorarioEspacioEntity {

	@EmbeddedId
	private HorarioEspacioPk idHorarioEspacio;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("idHorario")
	@JoinColumn(name = "ID_HORARIO")
	private HorarioEntity horario;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("idEspacioFisico")
	@JoinColumn(name = "ID_ESPACIO_FISICO")
	private EspacioFisicoEntity espacioFisico;
	
	@Column(name = "PRINCIPAL")
	private Boolean esPrincipal;
		
	public HorarioEspacioEntity(HorarioEntity horario, EspacioFisicoEntity espacioFisico, Boolean esPrincipal) {
		this.idHorarioEspacio = new HorarioEspacioPk(horario.getIdHorario(), espacioFisico.getIdEspacioFisico());
		this.horario = horario;
		this.espacioFisico = espacioFisico;
		this.esPrincipal=esPrincipal;
	}

	public HorarioEspacioEntity() {
	}

	public HorarioEspacioPk getIdHorarioEspacio() {
		return idHorarioEspacio;
	}

	public void setIdHorarioEspacio(HorarioEspacioPk idHorarioEspacio) {
		this.idHorarioEspacio = idHorarioEspacio;
	}

	public HorarioEntity getHorario() {
		return horario;
	}

	public void setHorario(HorarioEntity horario) {
		this.horario = horario;
	}

	public EspacioFisicoEntity getEspaciosFisico() {
		return espacioFisico;
	}

	public void setEspaciosFisico(EspacioFisicoEntity espaciosFisico) {
		this.espacioFisico = espaciosFisico;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}	

}