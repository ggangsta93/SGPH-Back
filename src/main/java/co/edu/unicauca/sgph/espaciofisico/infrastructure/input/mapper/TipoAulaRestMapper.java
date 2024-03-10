package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.TipoAulaInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoAulaOutDTO;

@Mapper(componentModel = "spring")
public interface TipoAulaRestMapper {

	TipoAulaOutDTO toTipoAulaOutDTO(TipoAula tipoAula);

	TipoAula toTipoAula(TipoAulaInDTO tipoAulaInDTO);

	List<TipoAulaOutDTO> toLstTipoAulaOutDTO(List<TipoAula> lstTipoAula);
}