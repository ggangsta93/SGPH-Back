package co.edu.unicauca.sgph.horario.domain.model;

import java.time.LocalTime;
import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.domain.model.Curso;

public class Horario {

	private Long idHorario;

	private DiaSemanaEnum dia;

	private LocalTime horaInicio;

	private LocalTime horaFin;

	private Curso curso;
	
	private List<Aula> aulas;

	public Long getIdHorario() {
		return idHorario;
	}

	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
}