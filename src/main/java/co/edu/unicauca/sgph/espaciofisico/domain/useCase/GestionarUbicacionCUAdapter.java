package co.edu.unicauca.sgph.espaciofisico.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarUbicacionCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarUbicacionGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Ubicacion;

public class GestionarUbicacionCUAdapter implements GestionarUbicacionCUIntPort {

	private final GestionarUbicacionGatewayIntPort gestionarUbicacionGatewayIntPort;

	public GestionarUbicacionCUAdapter(GestionarUbicacionGatewayIntPort gestionarUbicacionGatewayIntPort) {
		this.gestionarUbicacionGatewayIntPort = gestionarUbicacionGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarUbicacionCUIntPort#consultarUbicaciones()
	 */
	@Override
	public List<Ubicacion> consultarUbicaciones() {
		return this.gestionarUbicacionGatewayIntPort.consultarUbicaciones();
	}

}