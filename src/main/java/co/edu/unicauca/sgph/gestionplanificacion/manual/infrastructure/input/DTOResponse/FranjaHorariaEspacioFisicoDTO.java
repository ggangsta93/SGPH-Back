package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;
import java.time.LocalTime;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;

public class FranjaHorariaEspacioFisicoDTO implements Serializable {

	private static final long serialVersionUID = -7584575311009644688L;

	private Long idEspacioFisico;

	private DiaSemanaEnum dia;

	private LocalTime horaInicio;

	private LocalTime horaFin;

	private String nombreCurso;
	
	private Boolean esPrincipal;

	/**
	 * Constructor para obtener las franjas horarias de un espacio físico
	 * 
	 * @param idEspacioFisico
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param nombreCurso
	 * @param esPrincipal
	 */
	public FranjaHorariaEspacioFisicoDTO(Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin, String nombreCurso, Boolean esPrincipal) {
		this.idEspacioFisico = idEspacioFisico;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.nombreCurso = nombreCurso;
		this.esPrincipal=esPrincipal;
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

	public Boolean getEsPrincipal() {
		return esPrincipal;
	}

	public void setEsPrincipal(Boolean esPrincipal) {
		this.esPrincipal = esPrincipal;
	}
}