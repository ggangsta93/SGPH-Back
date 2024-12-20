package co.edu.unicauca.sgph.persona.domain.useCase;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

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

	@Override
	public Persona consultarPersonaPorEmail(String email) {
		return this.gestionarPersonaGatewayIntPort.consultarPersonaPorEmail(email);
	}

	@Override
	public Page<Persona> consultarPersonasPaginadas(Pageable pageable) {
		return this.gestionarPersonaGatewayIntPort.consultarPersonasPaginadas(pageable);
	}

	@Override
	public Persona obtenerPersonaPorId(Long idPersona) {
		return this.gestionarPersonaGatewayIntPort.obtenerPersonaPorId(idPersona);
	}

	@Override
	public void eliminarPersona(Long idPersona) {
		this.gestionarPersonaGatewayIntPort.eliminarPersona(idPersona);
	}

	@Override
	public Persona editarPersona(Persona persona) {
		Persona personaExistente = gestionarPersonaGatewayIntPort.findById(persona.getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada con el ID: " + persona.getIdPersona()));

        // Actualiza los campos editables
        personaExistente.setTipoIdentificacion(persona.getTipoIdentificacion());
        personaExistente.setNumeroIdentificacion(persona.getNumeroIdentificacion());
        personaExistente.setPrimerNombre(persona.getPrimerNombre());
        personaExistente.setSegundoNombre(persona.getSegundoNombre());
        personaExistente.setPrimerApellido(persona.getPrimerApellido());
        personaExistente.setSegundoApellido(persona.getSegundoApellido());
        personaExistente.setEmail(persona.getEmail());

        // Guarda la persona
        return gestionarPersonaGatewayIntPort.guardarPersona(personaExistente);
	}
}