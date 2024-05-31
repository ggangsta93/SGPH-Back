package co.edu.unicauca.sgph.periodoacademico.domain.useCase;

import java.util.Date;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.PeriodoAcademicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;

/**
 * <b>Descripción:<b> Clase que determina <br>
 * 
 * <b>Caso de Uso:<b>
 * 
 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
 * @version 1.0
 */
public class GestionarPeriodoAcademicoCUAdapter implements GestionarPeriodoAcademicoCUIntPort {

	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private PeriodoAcademicoFormatterResultsIntPort periodoAcademicoFormatterResultsIntPort;

	public GestionarPeriodoAcademicoCUAdapter(
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			PeriodoAcademicoFormatterResultsIntPort periodoAcademicoFormatterResultsIntPort) {
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.periodoAcademicoFormatterResultsIntPort = periodoAcademicoFormatterResultsIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort#guardarPeriodoAcademico(co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico)
	 */
	@Override
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		return this.gestionarPeriodoAcademicoGatewayIntPort.guardarPeriodoAcademico(periodoAcademico);
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort#actualizarEstadoPeriodoAcademicoAutomaticamente(java.util.Date)
	 */
	@Override
	public void actualizarEstadoPeriodoAcademicoAutomaticamente(Date fechaActual) {
		this.gestionarPeriodoAcademicoGatewayIntPort.actualizarEstadoPeriodoAcademicoAutomaticamente(fechaActual);
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort#consultarPeriodoAcademicoVigente()
	 */
	@Override
	public PeriodoAcademico consultarPeriodoAcademicoVigente() {
		return this.gestionarPeriodoAcademicoGatewayIntPort.consultarPeriodoAcademicoVigente();
	}

	/** 
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort#consultarPeriodosAcademicos(co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO)
	 */
	@Override
	public Page<PeriodoAcademicoOutDTO> consultarPeriodosAcademicos(FiltroPeriodoAcademicoDTO filtroPeriodoAcademicoDTO) {
		return this.gestionarPeriodoAcademicoGatewayIntPort.consultarPeriodosAcademicos(filtroPeriodoAcademicoDTO);
	}
}