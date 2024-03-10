package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.AulaInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.AulaOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoAulaOutDTO;

@Mapper(componentModel = "spring")
public interface AulaRestMapper {
	
	@Mapping(target = "idEdificio", expression = "java(aula.getEdificio().getIdEdificio())")
	@Mapping(target = "idTipoAula", expression = "java(aula.getTipoAula().getIdTipoAula())")
	AulaOutDTO toCursoOutDTO(Aula aula);
	
	@Mapping(target = "horarios", ignore = true)
	@Mapping(target = "recursosAula", ignore = true)
	@Mapping(target = "tipoAula", expression = "java(new TipoAula(aulaInDTO.getIdTipoAula()))")
	@Mapping(target = "edificio", expression = "java(new Edificio(aulaInDTO.getIdEdificio()))")
	Aula toCurso(AulaInDTO aulaInDTO);
	
	List<AulaOutDTO> toLstAulaOutDTO(List<Aula> lstAula);	
		
	List<TipoAulaOutDTO> toLstTipoAulaOutDTO(List<TipoAula> lstTipoAula);
}