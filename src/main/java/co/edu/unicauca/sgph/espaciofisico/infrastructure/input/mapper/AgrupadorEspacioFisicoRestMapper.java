package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.AgrupadorEspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;

@Mapper(componentModel = "spring")
public interface AgrupadorEspacioFisicoRestMapper {

	@Mapping(target = "idFacultad", source = "agrupadorEspacioFisico.facultad.idFacultad")
	AgrupadorEspacioFisicoOutDTO toAgrupadorEspacioFisicoOutDTO(AgrupadorEspacioFisico agrupadorEspacioFisico);

	@Mapping(target = "facultad", expression = "java(new Facultad(agrupadorEspacioFisicoInDTO.getIdFacultad()))")
	AgrupadorEspacioFisico toAgrupadorEspacioFisico(AgrupadorEspacioFisicoInDTO agrupadorEspacioFisicoInDTO);

	List<AgrupadorEspacioFisicoOutDTO> toLstAgrupadorEspacioFisicoOutDTO(
			List<AgrupadorEspacioFisico> lstAgrupadorEspacioFisico);
}