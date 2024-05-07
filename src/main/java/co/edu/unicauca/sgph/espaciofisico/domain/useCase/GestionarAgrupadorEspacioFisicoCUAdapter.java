package co.edu.unicauca.sgph.espaciofisico.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.AgrupadorEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;

public class GestionarAgrupadorEspacioFisicoCUAdapter implements GestionarAgrupadorEspacioFisicoCUIntPort {

	private final GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort;
	private final AgrupadorEspacioFisicoFormatterResultsIntPort agrupadorEspacioFisicoFormatterResultsIntPort;

	public GestionarAgrupadorEspacioFisicoCUAdapter(
			GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort,
			AgrupadorEspacioFisicoFormatterResultsIntPort agrupadorEspacioFisicoFormatterResultsIntPort) {
		this.gestionarAgrupadorEspacioFisicoGatewayIntPort = gestionarAgrupadorEspacioFisicoGatewayIntPort;
		this.agrupadorEspacioFisicoFormatterResultsIntPort = agrupadorEspacioFisicoFormatterResultsIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort#consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			List<Long> idAgrupadorEspacioFisico) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort#consultarAgrupadoresEspaciosFisicosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad);
	}
}