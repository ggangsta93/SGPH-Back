package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico;

public interface GestionarPeriodoAcademicoGatewayIntPort {

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
