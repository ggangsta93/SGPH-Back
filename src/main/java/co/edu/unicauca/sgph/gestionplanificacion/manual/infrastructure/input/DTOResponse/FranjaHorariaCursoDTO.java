package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaCursoDTO implements Serializable {

	private static final long serialVersionUID = 7792736941477578003L;

	private Long idHorario;

	private Long idEspacioFisico;

	private DiaSemanaEnum dia;

	private String horaInicio;

	private String horaFin;

	public FranjaHorariaCursoDTO() {
	}

	public FranjaHorariaCursoDTO(Long idHorario, Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
		this.idHorario = idHorario;
		this.idEspacioFisico = idEspacioFisico;
		this.dia = dia;
		this.horaInicio = horaInicio.toString();
		this.horaFin = horaFin.toString();
	}

	public Long getIdEspacioFisico() {
		return idEspacioFisico;
	}

	public void setIdEspacioFisico(Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
	}

	public DiaSemanaEnum getDia() {
		return dia;
	}

	public void setDia(DiaSemanaEnum dia) {
		this.dia = dia;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}
}