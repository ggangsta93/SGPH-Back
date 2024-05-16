package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaBasicaDTO implements Serializable {

	/**
	 * Atributo que determina
	 */
	private static final long serialVersionUID = 8695965823131566795L;

	protected DiaSemanaEnum dia;

	protected LocalTime horaInicio;

	protected LocalTime horaFin;

	public FranjaHorariaBasicaDTO() {
	}

	public FranjaHorariaBasicaDTO(DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}
	
	public FranjaHorariaBasicaDTO(LocalTime horaInicio, LocalTime horaFin) {
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	public DiaSemanaEnum getDia() {
		return dia;
	}

	public void setDia(DiaSemanaEnum dia) {
		this.dia = dia;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
}