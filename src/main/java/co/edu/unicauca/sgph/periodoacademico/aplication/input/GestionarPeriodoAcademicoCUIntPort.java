package co.edu.unicauca.sgph.periodoacademico.aplication.input;

import java.util.Date;

import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;

public interface GestionarPeriodoAcademicoCUIntPort {

	/**
	 * Método encargado de guardar o actualizar un periodo académico <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param periodoAcademico
	 * @return
	 */
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico);

	/**
	 * Método encargado de validar si la fecha actual del sistema se encuentra
	 * dentro del rango de fechas de inicio y fin del último período académico, y
	 * actualiza su estado a 'CERRADO' si no está dentro de dicho rango.<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param fechaActual Fecha actual del sistema
	 */
	public void actualizarEstadoPeriodoAcademicoAutomaticamente(Date fechaActual);

	/**
	 * Método encargado de consultar el periodo académico vigente<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public PeriodoAcademico consultarPeriodoAcademicoVigente();
}