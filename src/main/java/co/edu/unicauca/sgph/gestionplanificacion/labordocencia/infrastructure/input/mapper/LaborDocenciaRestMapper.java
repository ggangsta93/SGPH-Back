package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse.CursoOutDTO;

@Mapper(componentModel = "spring")
public interface LaborDocenciaRestMapper {

	@Mapping(target = "idAsignatura", source = "curso.asignatura.idAsignatura")
	CursoOutDTO toCursoOutDTO(Curso curso);

	@Mapping(target = "asignatura", expression = "java(new Asignatura(cursoInDTO.getIdAsignatura()))")
	@Mapping(target = "docentes", ignore = true)
	Curso toCurso(CursoInDTO cursoInDTO);

	List<CursoOutDTO> toLstCursoOutDTO(List<Curso> lstCurso);
}