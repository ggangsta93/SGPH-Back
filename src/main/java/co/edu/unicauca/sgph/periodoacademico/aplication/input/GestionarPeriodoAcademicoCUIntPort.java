package co.edu.unicauca.sgph.periodoacademico.aplication.input;

import java.util.Date;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;

public interface GestionarPeriodoAcademicoCUIntPort {

	/**
	 * Método encargado de guardar o actualizar un periodo académico <br>
	 * 
	 * @author apedro
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
	 * @author apedro
	 * 
	 * @param fechaActual Fecha actual del sistema
	 */
	public void actualizarEstadoPeriodoAcademicoAutomaticamente(Date fechaActual);

	/**
	 * Método encargado de consultar el periodo académico vigente<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public PeriodoAcademico consultarPeriodoAcademicoVigente();

	/**
	 * Método encargado de consultar los periodos académicos <br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroPeriodoAcademicoDTO
	 * @return Page de instancias PeriodoAcademicoOutDTO
	 */
	public Page<PeriodoAcademicoOutDTO> consultarPeriodosAcademicos(FiltroPeriodoAcademicoDTO filtroPeriodoAcademicoDTO);

}