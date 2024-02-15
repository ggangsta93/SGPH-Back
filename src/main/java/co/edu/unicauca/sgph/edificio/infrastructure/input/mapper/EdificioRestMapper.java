package co.edu.unicauca.sgph.edificio.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;
import co.edu.unicauca.sgph.edificio.infrastructure.input.DTORequest.EdificioInDTO;
import co.edu.unicauca.sgph.edificio.infrastructure.input.DTOResponse.EdificioOutDTO;

@Mapper(componentModel = "spring")
public interface EdificioRestMapper {

	@Mapping(target = "idFacultad", expression = "java(edificio.getFacultad().getIdFacultad())")
	EdificioOutDTO toEdificioOutDTO(Edificio edificio);

	@Mapping(target = "facultad", expression = "java(new Facultad(edificioInDTO.getIdFacultad()))")
	Edificio toEdificio(EdificioInDTO edificioInDTO);

	List<EdificioOutDTO> toLstEdificioOutDTO(List<Edificio> lstEdificio);
}