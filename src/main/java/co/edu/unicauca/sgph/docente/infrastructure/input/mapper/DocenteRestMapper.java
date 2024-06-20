package co.edu.unicauca.sgph.docente.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

@Mapper(componentModel = "spring")
public interface DocenteRestMapper {

	@Mapping(target = "idTipoIdentificacion", source = "docente.tipoIdentificacion.idTipoIdentificacion")
	@Mapping(target = "codigoTipoIdentificacion", source = "docente.tipoIdentificacion.codigoTipoIdentificacion")
	@Mapping(target = "idDepartamento", source = "docente.departamento.idDepartamento")
	DocenteOutDTO toDocenteOutDTO(Docente docente);

	@Mapping(target = "tipoIdentificacion", expression = "java(new TipoIdentificacion(docenteInDTO.getIdTipoIdentificacion()))")
	@Mapping(target = "departamento", expression = "java(new Departamento(docenteInDTO.getIdDepartamento()))")
	Docente toDocente(DocenteInDTO docenteInDTO);

	List<DocenteOutDTO> toLstDocenteOutDTO(List<Docente> lstDocente);
}