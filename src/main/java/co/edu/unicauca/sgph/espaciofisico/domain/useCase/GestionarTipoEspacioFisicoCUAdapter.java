package co.edu.unicauca.sgph.espaciofisico.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarTipoEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarTipoEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.TipoEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;

public class GestionarTipoEspacioFisicoCUAdapter implements GestionarTipoEspacioFisicoCUIntPort {

	private final GestionarTipoEspacioFisicoGatewayIntPort gestionarTipoEspacioFisicoGatewayIntPort;
	private final TipoEspacioFisicoFormatterResultsIntPort tipoEspacioFisicoFormatterResultsIntPort;

	public GestionarTipoEspacioFisicoCUAdapter(GestionarTipoEspacioFisicoGatewayIntPort gestionarTipoEspacioFisicoGatewayIntPort,
			TipoEspacioFisicoFormatterResultsIntPort tipoEspacioFisicoFormatterResultsIntPort) {
		this.gestionarTipoEspacioFisicoGatewayIntPort = gestionarTipoEspacioFisicoGatewayIntPort;
		this.tipoEspacioFisicoFormatterResultsIntPort = tipoEspacioFisicoFormatterResultsIntPort;
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarTipoEspacioFisicoCUIntPort#consultarTiposEspaciosFisicosPorUbicaciones(java.util.List)
	 */
	@Override
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorUbicaciones(List<Long> lstIdUbicacion) {
		return this.gestionarTipoEspacioFisicoGatewayIntPort.consultarTiposEspaciosFisicosPorUbicaciones(lstIdUbicacion);
	}

}