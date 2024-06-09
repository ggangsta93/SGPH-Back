package co.edu.unicauca.sgph.periodoacademico.aplication.output;

import java.util.Date;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;

public interface GestionarPeriodoAcademicoGatewayIntPort {

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

	/**
	 * Método encargado de validar si existe un periodo académico en un determinado
	 * año. Este es utilizado por la anotación @ExistsByAnioAndPeriodo<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param anio    Año del periodo académico
	 * @param periodo Periodo académico
	 * @return
	 */
	public PeriodoAcademico existsByAnioAndPeriodo(Long anio, Long periodo);

	/**
	 * Método encargado de consultar los periodos académicos <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param filtroPeriodoAcademicoDTO
	 * @return Page de instancias PeriodoAcademicoOutDTO
	 */
	public Page<PeriodoAcademicoOutDTO> consultarPeriodosAcademicos(
			FiltroPeriodoAcademicoDTO filtroPeriodoAcademicoDTO);

	/**
	 * Método encargado de validar si existe una fecha fin mayor a la fecha de
	 * inicio del periodo que se quiere guardar o actualizar.Este es utilizado por
	 * la anotación FechaInicioGreaterThanUltimaFechaFin<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param fechaInicio Fecha de inicio
	 * @return
	 */
	public Date esFechaInicioGreaterThanUltimaFechaFin(Date fechaInicio);
	
	
	/**
	 * Método encargado de consultar la información del periodo académico dado su
	 * identificador único </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPeriodoAcademico Identificador único del periodo académico
	 * @return
	 */
	public PeriodoAcademico consultarPeriodoAcademicoPorId(Long idPeriodoAcademico);

}
