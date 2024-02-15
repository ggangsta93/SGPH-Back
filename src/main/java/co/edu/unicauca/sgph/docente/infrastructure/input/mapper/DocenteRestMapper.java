package co.edu.unicauca.sgph.docente.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

@Mapper(componentModel = "spring")
public interface DocenteRestMapper {

	@Mapping(target = "idTipoIdentificacion", expression = "java(docente.getTipoIdentificacion().getIdTipoIdentificacion())")
	@Mapping(target = "codigoTipoIdentificacion", expression = "java(docente.getTipoIdentificacion().getCodigoTipoIdentificacion())")
	DocenteOutDTO toDocenteOutDTO(Docente docente);

	@Mapping(target = "tipoIdentificacion", expression = "java(new TipoIdentificacion(docenteInDTO.getIdTipoIdentificacion()))")
	Docente toDocente(DocenteInDTO docenteInDTO);

	List<DocenteOutDTO> toLstDocenteOutDTO(List<Docente> lstDocente);
}