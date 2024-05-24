package co.edu.unicauca.sgph.espaciofisico.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEdificioCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;

public class GestionarEdificioCUAdapter implements GestionarEdificioCUIntPort {

	private final GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort;

	public GestionarEdificioCUAdapter(GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort) {
		this.gestionarEdificioGatewayIntPort = gestionarEdificioGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEdificioCUIntPort#consultarEdificios()
	 */
	@Override
	public List<Edificio> consultarEdificios() {
		return this.gestionarEdificioGatewayIntPort.consultarEdificios();
	}

}