package co.edu.unicauca.sgph.agrupador.infrastructure.input.mapper;

import java.util.List;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AgrupadorEspacioFisicoInDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgrupadorEspacioFisicoRestMapper {

	@Mapping(target = "idFacultad", source = "agrupadorEspacioFisico.facultad.idFacultad")
	AgrupadorEspacioFisicoOutDTO toAgrupadorEspacioFisicoOutDTO(AgrupadorEspacioFisico agrupadorEspacioFisico);

	@Mapping(target = "facultad", expression = "java(new Facultad(agrupadorEspacioFisicoInDTO.getIdFacultad()))")
	AgrupadorEspacioFisico toAgrupadorEspacioFisico(AgrupadorEspacioFisicoInDTO agrupadorEspacioFisicoInDTO);

	List<AgrupadorEspacioFisicoOutDTO> toLstAgrupadorEspacioFisicoOutDTO(
			List<AgrupadorEspacioFisico> lstAgrupadorEspacioFisico);
	@Mapping(source = "facultad.nombre", target = "nombreFacultad")
	@Mapping(source = "facultad.idFacultad", target = "idFacultad")
	AgrupadorEspacioFisicoDTO toAgrupadorEspacioFisicoDTO(AgrupadorEspacioFisico agrupadorEspacioFisico);
}