package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaDocenteDTO implements Serializable {

	private static final long serialVersionUID = -7584575311009644688L;

	private Long idHorario;
	
	private DiaSemanaEnum dia;

	private LocalTime horaInicio;

	private LocalTime horaFin;

	private String nombreCurso;
	
	private String salon;

	/**
	 * Constructor para obtener las franjas horarias de un docente
	 * 
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param nombreCurso 
	 */
	public FranjaHorariaDocenteDTO(Long idHorario, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin, String nombreCurso) {
		this.idHorario=idHorario;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.nombreCurso = nombreCurso;
	}

	/**
	 * Constructor para obtener las franjas horarias
	 * 
	 * @param idHorario
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 */
	public FranjaHorariaDocenteDTO(Long idHorario, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
		this.idHorario = idHorario;
		this.dia = dia;
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

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}

	public String getSalon() {
		return salon;
	}

	public void setSalon(String salon) {
		this.salon = salon;
	}	
}