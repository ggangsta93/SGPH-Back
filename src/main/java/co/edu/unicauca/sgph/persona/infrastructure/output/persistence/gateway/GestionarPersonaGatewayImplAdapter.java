package co.edu.unicauca.sgph.persona.infrastructure.output.persistence.gateway;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity.PersonaEntity;
import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.repository.PersonaRepositoryInt;

@Service
public class GestionarPersonaGatewayImplAdapter implements GestionarPersonaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private PersonaRepositoryInt personaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarPersonaGatewayImplAdapter(PersonaRepositoryInt personaRepositoryInt, ModelMapper modelMapper) {
		this.personaRepositoryInt = personaRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#guardarPersona(co.edu.unicauca.sgph.persona.domain.model.Persona)
	 */
	@Override
	public Persona guardarPersona(Persona persona) {
		return this.modelMapper.map(this.personaRepositoryInt.save(this.modelMapper.map(persona, PersonaEntity.class)),
				Persona.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#existePersonaPorEmail(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public Boolean existePersonaPorEmail(String email, Long idPersona) {
		PersonaEntity personaEntity = this.personaRepositoryInt.consultarPersonaPorEmail(email, idPersona);
		if (Objects.nonNull(personaEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#existePersonaPorIdentificacion(java.lang.Long,
	 *      java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean existePersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion,
			Long idPersona) {
		PersonaEntity personaEntity = this.personaRepositoryInt
				.consultarPersonaPorTipoYNumeroIdentificacion(idTipoIdentificacion, numeroIdentificacion, idPersona);
		if (Objects.nonNull(personaEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#consultarPersonaPorIdentificacion(java.lang.Long, java.lang.String)
	 */
	@Override
	public Persona consultarPersonaPorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion) {
		PersonaEntity personaEntity = this.personaRepositoryInt
				.consultarPersonaPorTipoYNumeroIdentificacion(idTipoIdentificacion, numeroIdentificacion, null);
		if (Objects.isNull(personaEntity)) {
			return null;
		}
		return this.modelMapper.map(personaEntity, Persona.class);
	}

	/** 
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#existePersonaPorIdPersona(java.lang.Long)
	 */
	@Override
	public Boolean existePersonaPorIdPersona(Long idPersona) {
		PersonaEntity personaEntity = this.personaRepositoryInt.consultarPersonaPorIdPersona(idPersona);
		if (Objects.nonNull(personaEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort#consultarTiposIdentificacion()
	 */
	@Override
	public List<TipoIdentificacion> consultarTiposIdentificacion() {
		return this.modelMapper.map(this.personaRepositoryInt.consultarTiposIdentificacion(),
				new TypeToken<List<TipoIdentificacion>>() {
				}.getType());
	}

	@Override
	public Persona consultarPersonaPorEmail(String email) {
		PersonaEntity personaEntity = this.personaRepositoryInt.findByEmail(email);
		if (Objects.isNull(personaEntity)) {
			return null;
		}
		return this.modelMapper.map(personaEntity, Persona.class);
	}
}
