package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaCursoAsociarInDTO {

	private Long idHorario;

	private Long idEspacioFisico;

	private DiaSemanaEnum dia;

	private String horaInicio;

	private String horaFin;
	
	
	public FranjaHorariaCursoAsociarInDTO() {
	}

	public FranjaHorariaCursoAsociarInDTO(DiaSemanaEnum dia, String horaInicio, String horaFin,Long idEspacioFisico) {
		this.idEspacioFisico = idEspacioFisico;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
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
}