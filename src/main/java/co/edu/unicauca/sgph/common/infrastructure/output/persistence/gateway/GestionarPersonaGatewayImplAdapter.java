package co.edu.unicauca.sgph.common.infrastructure.output.persistence.gateway;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.common.domain.model.Persona;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.repository.PersonaRepositoryInt;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;

@Service
public class GestionarPersonaGatewayImplAdapter implements GestionarPersonaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;

	private PersonaRepositoryInt personaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarPersonaGatewayImplAdapter(PersonaRepositoryInt personaRepositoryInt, ModelMapper modelMapper,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort) {
		this.personaRepositoryInt = personaRepositoryInt;
		this.modelMapper = modelMapper;
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort#existsPersonaByEmail(java.lang.String,
	 *      java.lang.Long)
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
	 * @see co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort#existsPersonaByTipoAndNumeroIdentificacion(java.lang.Long,
	 *      java.lang.String, java.lang.Long)
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

	/**
	 * @see co.edu.unicauca.sgph.common.aplication.output.GestionarPersonaGatewayIntPort#consultarPersonaPorTipoYNumeroIdentificacion(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public Persona consultarPersonaPorTipoYNumeroIdentificacion(Long idTipoIdentificacion,
			String numeroIdentificacion) {
		PersonaEntity personaEntity = this.personaRepositoryInt
				.consultarPersonaPorTipoYNumeroIdentificacion(idTipoIdentificacion, numeroIdentificacion, null);
		if (Objects.isNull(personaEntity)) {
			return null;
		}

		Docente docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorIdentificacion(idTipoIdentificacion,
				numeroIdentificacion);

		if (Objects.isNull(docente)) {
			personaEntity.setEsDocente(Boolean.FALSE);
		} else {
			personaEntity.setEsDocente(Boolean.TRUE);
		}
		return this.modelMapper.map(personaEntity, Persona.class);
	}
}
