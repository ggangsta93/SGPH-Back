package co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model;

import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaBasicaDTO {

	private Long idAsignatura;
	
	private Long idHorario;

	private Long idEspacioFisico;

	private DiaSemanaEnum dia;

	private LocalTime horaInicio;

	private LocalTime horaFin;

	public FranjaHorariaBasicaDTO() {
	}
	
	
	/**
	 * Constructor de la clase para las franjas de cursos por semestre
	 * 
	 * @param idAsignatura
	 * @param idHorario
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 */
	public FranjaHorariaBasicaDTO(Long idHorario, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin, Long idAsignatura) {
		this.idAsignatura = idAsignatura;
		this.idHorario = idHorario;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Constructor de la clase para las franjas de los espacios físicos
	 * 
	 * @param idHorario
	 * @param idEspacioFisico
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 */
	public FranjaHorariaBasicaDTO(Long idHorario, Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin) {
		this.idHorario = idHorario;
		this.idEspacioFisico = idEspacioFisico;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Constructor de la clase para las franjas de los docentes
	 * 
	 * @param idHorario
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 */
	public FranjaHorariaBasicaDTO(Long idHorario, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
		this.idHorario = idHorario;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
	}

	/**
	 * Constructor de la clase para las franjas restringidas
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 */
	public FranjaHorariaBasicaDTO(DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin) {
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

	public Long getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}
}