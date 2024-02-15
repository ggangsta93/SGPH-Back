package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse;

import java.io.Serializable;

public class InfoGeneralCursosPorProgramaDTO implements Serializable {

	private static final long serialVersionUID = 5901835328483292426L;

	private Long totalCursos;

	private Long cantidadCursosHorarioParcial;

	private Long cantidadCursosSinHorario;
	
	private Long cantidadCursosConHorario;
	
	private Long cantidadCursosSinDocente;
		
	public InfoGeneralCursosPorProgramaDTO() {
		//Constructor sin parámetros
	}
	
	public Long getTotalCursos() {
		return totalCursos;
	}

	public void setTotalCursos(Long totalCursos) {
		this.totalCursos = totalCursos;
	}

	public Long getCantidadCursosHorarioParcial() {
		return cantidadCursosHorarioParcial;
	}

	public void setCantidadCursosHorarioParcial(Long cantidadCursosHorarioParcial) {
		this.cantidadCursosHorarioParcial = cantidadCursosHorarioParcial;
	}

	public Long getCantidadCursosSinHorario() {
		return cantidadCursosSinHorario;
	}

	public void setCantidadCursosSinHorario(Long cantidadCursosSinHorario) {
		this.cantidadCursosSinHorario = cantidadCursosSinHorario;
	}

	public Long getCantidadCursosSinDocente() {
		return cantidadCursosSinDocente;
	}

	public void setCantidadCursosSinDocente(Long cantidadCursosSinDocente) {
		this.cantidadCursosSinDocente = cantidadCursosSinDocente;
	}
	
	public Long getCantidadCursosConHorario() {
		return cantidadCursosConHorario;
	}
	
	public void setCantidadCursosConHorario(Long cantidadCursosConHorario) {
		this.cantidadCursosConHorario = cantidadCursosConHorario;
	}	
}