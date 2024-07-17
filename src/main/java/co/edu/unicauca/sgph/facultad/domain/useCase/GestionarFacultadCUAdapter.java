package co.edu.unicauca.sgph.facultad.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.facultad.aplication.input.GestionarFacultadCUIntPort;
import co.edu.unicauca.sgph.facultad.aplication.output.FacultadFormatterResultsIntPort;
import co.edu.unicauca.sgph.facultad.aplication.output.GestionarFacultadGatewayIntPort;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public class GestionarFacultadCUAdapter implements GestionarFacultadCUIntPort {

	private final GestionarFacultadGatewayIntPort gestionarFacultadGatewayIntPort;
	private final FacultadFormatterResultsIntPort facultadFormatterResultsIntPort;

	public GestionarFacultadCUAdapter(GestionarFacultadGatewayIntPort gestionarFacultadGatewayIntPort,
			FacultadFormatterResultsIntPort facultadFormatterResultsIntPort) {
		this.gestionarFacultadGatewayIntPort = gestionarFacultadGatewayIntPort;
		this.facultadFormatterResultsIntPort = facultadFormatterResultsIntPort;
	}


	@Override
	public Facultad guardarFacultad(Facultad facultad) {
		return this.gestionarFacultadGatewayIntPort.guardarFacultad(facultad);
	}

	/** 
	 * @see co.edu.unicauca.sgph.aplication.input.GestionarFacultadCUIntPort#consultarFacultades()
	 */
	@Override
	public List<Facultad> consultarFacultades() {
		return this.gestionarFacultadGatewayIntPort.consultarFacultades();
	}
}