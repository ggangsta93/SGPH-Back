package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EdificioOutDTO;

@Mapper(componentModel = "spring")
public interface EdificioRestMapper {

	EdificioOutDTO toEdificioOutDTO(Edificio edificio);

	List<EdificioOutDTO> toLstEdificioOutDTO(List<Edificio> lstEdificio);
}