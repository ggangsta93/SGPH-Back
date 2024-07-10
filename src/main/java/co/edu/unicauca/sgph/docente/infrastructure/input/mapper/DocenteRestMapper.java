package co.edu.unicauca.sgph.docente.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

@Mapper(componentModel = "spring")
public interface DocenteRestMapper {

	@Mapping(target = "idPersona", source = "docente.persona.idPersona")
	@Mapping(target = "idTipoIdentificacion", source = "docente.persona.tipoIdentificacion.idTipoIdentificacion")
	@Mapping(target = "codigoTipoIdentificacion", source = "docente.persona.tipoIdentificacion.codigoTipoIdentificacion")
	@Mapping(target = "numeroIdentificacion", source = "docente.persona.numeroIdentificacion")
	@Mapping(target = "primerNombre", source = "docente.persona.primerNombre")	
	@Mapping(target = "segundoNombre", source = "docente.persona.segundoNombre")	
	@Mapping(target = "primerApellido", source = "docente.persona.primerApellido")	
	@Mapping(target = "segundoApellido", source = "docente.persona.segundoApellido")	
	@Mapping(target = "email", source = "docente.persona.email")		
	@Mapping(target = "idDepartamento", source = "docente.departamento.idDepartamento")	
	DocenteOutDTO toDocenteOutDTO(Docente docente);

	@Mapping(target = "persona", expression = "java(new Persona(docenteInDTO.getIdPersona()))")
	@Mapping(target = "departamento", expression = "java(docenteInDTO.getIdDepartamento() != null ? new Departamento(docenteInDTO.getIdDepartamento()) : null)")
	@Mapping(target = "cursos", ignore=true)
	Docente toDocente(DocenteInDTO docenteInDTO);

	List<DocenteOutDTO> toLstDocenteOutDTO(List<Docente> lstDocente);
}