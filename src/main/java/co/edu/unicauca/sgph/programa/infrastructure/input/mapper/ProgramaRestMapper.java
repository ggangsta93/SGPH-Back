package co.edu.unicauca.sgph.programa.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;

@Mapper(componentModel = "spring")
public interface ProgramaRestMapper {

	@Mapping(target = "idFacultad", expression = "java(programa.getIdPrograma())")
	ProgramaOutDTO toProgramaOutDTO(Programa programa);

	@Mapping(target = "facultad", expression = "java(new Facultad(ProgramaInDTO.getIdFacultad()))")
	@Mapping(target = "asignaturas", ignore = true)
	Programa toPrograma(ProgramaInDTO ProgramaInDTO);

	List<ProgramaOutDTO> toLstProgramaOutDTO(List<Programa> lstPrograma);
}