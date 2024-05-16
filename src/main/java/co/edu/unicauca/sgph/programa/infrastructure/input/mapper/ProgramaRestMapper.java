package co.edu.unicauca.sgph.programa.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;

@Mapper(componentModel = "spring")
public interface ProgramaRestMapper {

	@Mapping(target = "idFacultad", source = "programa.facultad.idFacultad")
	ProgramaOutDTO toProgramaOutDTO(Programa programa);

	@Mapping(target = "facultad", expression = "java(new Facultad(programaInDTO.getIdFacultad()))")
	@Mapping(target = "asignaturas", ignore = true)
	Programa toPrograma(ProgramaInDTO programaInDTO);

	List<ProgramaOutDTO> toLstProgramaOutDTO(List<Programa> lstPrograma);
}