package co.edu.unicauca.sgph.persona.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.persona.aplication.output.PersonaFormatterResultsIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;

public class GestionarPersonaCUAdapter implements GestionarPersonaCUIntPort {

	private final GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort;
	private final PersonaFormatterResultsIntPort personaFormatterResultsIntPort;

	public GestionarPersonaCUAdapter(GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort,
			PersonaFormatterResultsIntPort personaFormatterResultsIntPort) {
		this.gestionarPersonaGatewayIntPort = gestionarPersonaGatewayIntPort;
		this.personaFormatterResultsIntPort = personaFormatterResultsIntPort;
	}

	@Override
	public Persona guardarPersona(Persona persona) {
		return this.gestionarPersonaGatewayIntPort.guardarPersona(persona);
	}

	/**
	 * @see co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort#consultarPersonaPorIdentificacion(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public Persona consultarPersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion) {
		return this.gestionarPersonaGatewayIntPort.consultarPersonaPorIdentificacion(idTipoIdentificacion,
				numeroIdentificacion);
	}

	/** 
	 * @see co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort#consultarTiposIdentificacion()
	 */
	@Override
	public List<TipoIdentificacion> consultarTiposIdentificacion() {
		return this.gestionarPersonaGatewayIntPort.consultarTiposIdentificacion();
	}
}