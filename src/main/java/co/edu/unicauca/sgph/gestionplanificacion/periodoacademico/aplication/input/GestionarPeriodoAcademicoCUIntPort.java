package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.input;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico;

public interface GestionarPeriodoAcademicoCUIntPort {

	/**
	 * Método encargado de guardar o actualizar un periodo academico </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param periodoAcademico
	 * @return
	 */
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico);
	
}