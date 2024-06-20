package co.edu.unicauca.sgph.espaciofisico.domain.model;

import co.edu.unicauca.sgph.horario.domain.model.Horario;

public class HorarioEspacio {

	private Horario horario;

	private EspacioFisico espacioFisico;

	private Boolean esPrincipal;

	public HorarioEspacio(Long idHorario, Long idEspacioFisico, Boolean esPrincipal) {
		this.horario = new Horario(idHorario);
		this.espacioFisico = new EspacioFisico(idEspacioFisico);
		this.esPrincipal = esPrincipal;
	}

	public HorarioEspacio() {
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public EspacioFisico getEspacioFisico() {
		return espacioFisico;
	}

	public void setEspacioFisico(EspacioFisico espacioFisico) {
		this.espacioFisico = espacioFisico;
	}

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
}