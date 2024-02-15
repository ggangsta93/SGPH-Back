package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.useCase;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.PeriodoAcademicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico;

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
	 * @see co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort#guardarPeriodoAcademico(co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico)
	 */
	@Override
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		return this.gestionarPeriodoAcademicoGatewayIntPort.guardarPeriodoAcademico(periodoAcademico);
	}

}