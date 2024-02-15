package co.edu.unicauca.sgph.curso.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse.CursoOutDTO;

@Mapper(componentModel = "spring")
public interface CursoRestMapper {

	@Mapping(target = "idAsignatura", expression = "java(curso.getAsignatura().getIdAsignatura())")
	@Mapping(target = "idPeriodoAcademico", expression = "java(curso.getPeriodoAcademico().getIdPeriodoAcademico())")
	CursoOutDTO toCursoOutDTO(Curso curso);

	@Mapping(target = "asignatura", expression = "java(new Asignatura(cursoInDTO.getIdAsignatura()))")
	@Mapping(target = "periodoAcademico", expression = "java(new PeriodoAcademico(cursoInDTO.getIdPeriodoAcademico()))")
	@Mapping(target = "docentes", ignore = true)
	Curso toCurso(CursoInDTO cursoInDTO);

	List<CursoOutDTO> toLstCursoOutDTO(List<Curso> lstCurso);
}