package co.edu.unicauca.sgph.aula.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.aula.domain.model.TipoAula;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest.TipoAulaInDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.TipoAulaOutDTO;

@Mapper(componentModel = "spring")
public interface TipoAulaRestMapper {

	TipoAulaOutDTO toTipoAulaOutDTO(TipoAula tipoAula);

	TipoAula toTipoAula(TipoAulaInDTO tipoAulaInDTO);

	List<TipoAulaOutDTO> toLstTipoAulaOutDTO(List<TipoAula> lstTipoAula);
}