package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.TipoEspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoEspacioFisicoOutDTO;

@Mapper(componentModel = "spring")
public interface TipoEspacioFisicoRestMapper {

	TipoEspacioFisicoOutDTO toTipoEspacioFisicoOutDTO(TipoEspacioFisico tipoEspacioFisico);

	TipoEspacioFisico toTipoEspacioFisico(TipoEspacioFisicoInDTO tipoEspacioFisicoInDTO);

	List<TipoEspacioFisicoOutDTO> toLstTipoEspacioFisicoOutDTO(List<TipoEspacioFisico> lstTipoEspacioFisico);
}