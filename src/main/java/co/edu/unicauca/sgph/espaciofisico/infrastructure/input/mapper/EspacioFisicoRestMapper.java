package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoEspacioFisicoOutDTO;

@Mapper(componentModel = "spring")
public interface EspacioFisicoRestMapper {

	@Mapping(target = "idEdificio", expression = "java(espacioFisico.getEdificio().getIdEdificio())")
	@Mapping(target = "idTipoEspacioFisico", expression = "java(espacioFisico.getTipoEspacioFisico().getIdTipoEspacioFisico())")
	EspacioFisicoOutDTO toEspacioFisicoOutDTO(EspacioFisico espacioFisico);

	@Mapping(target = "horarios", ignore = true)
	@Mapping(target = "recursosEspacioFisico", ignore = true)
	@Mapping(target = "tipoEspacioFisico", expression = "java(new TipoEspacioFisico(espacioFisicoInDTO.getIdTipoEspacioFisico()))")
	@Mapping(target = "edificio", expression = "java(new Edificio(espacioFisicoInDTO.getIdEdificio()))")
	EspacioFisico toEspacioFisico(EspacioFisicoInDTO espacioFisicoInDTO);

	List<EspacioFisicoOutDTO> toLstEspacioFisicoOutDTO(List<EspacioFisico> lstEspacioFisico);

	List<TipoEspacioFisicoOutDTO> toLstTipoEspacioFisicoOutDTO(List<TipoEspacioFisico> lstTipoEspacioFisico);
}