package co.edu.unicauca.sgph.common.infrastructure.output.persistence.gateway;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.repository.PersonaRepositoryInt;

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
	 * @see co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort#existsPersonaByEmail(java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean existsPersonaByEmail(String email, Long idPersona) {
		PersonaEntity personaEntity = this.personaRepositoryInt.consultarPersonaPorEmail(email, idPersona);
		if (Objects.nonNull(personaEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort#existsPersonaByTipoAndNumeroIdentificacion(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean existsPersonaByTipoAndNumeroIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion,
			Long idPersona) {
		PersonaEntity personaEntity = this.personaRepositoryInt
				.consultarPersonaPorTipoYNumeroIdentificacion(idTipoIdentificacion, numeroIdentificacion, idPersona);
		if (Objects.nonNull(personaEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
