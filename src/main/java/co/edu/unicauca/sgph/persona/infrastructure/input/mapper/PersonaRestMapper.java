package co.edu.unicauca.sgph.persona.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest.PersonaInDTO;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTOResponse.PersonaOutDTO;

@Mapper(componentModel = "spring")
public interface PersonaRestMapper {	
	
	@Mapping(target = "idTipoIdentificacion", source = "persona.tipoIdentificacion.idTipoIdentificacion")
	@Mapping(target = "codigoTipoIdentificacion", source = "persona.tipoIdentificacion.codigoTipoIdentificacion")
	PersonaOutDTO toPersonaOutDTO(Persona persona);

	@Mapping(target = "tipoIdentificacion", expression = "java(new TipoIdentificacion(personaInDTO.getIdTipoIdentificacion()))")
	@Mapping(target = "sinReferencia", ignore = true)
	Persona toPersona(PersonaInDTO personaInDTO);

	List<PersonaOutDTO> toLstPersonaOutDTO(List<Persona> lstPersona);
}