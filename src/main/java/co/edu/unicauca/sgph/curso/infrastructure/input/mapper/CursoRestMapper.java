package co.edu.unicauca.sgph.curso.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTOResponse.CursoOutDTO;

@Mapper(componentModel = "spring")
public interface CursoRestMapper {

	@Mapping(target = "idAsignatura", source = "curso.asignatura.idAsignatura")
	@Mapping(target = "idPeriodoAcademico", source = "curso.periodoAcademico.idPeriodoAcademico")
	@Mapping(target = "idPrograma", source = "curso.asignatura.programa.idPrograma")
	@Mapping(target = "idFacultad", source = "curso.asignatura.programa.facultad.idFacultad")
	@Mapping(target = "nombreCurso", source = "curso.asignatura.nombre")
	@Mapping(target = "nombreFacultad", source = "curso.asignatura.programa.facultad.nombre")
	@Mapping(target = "nombrePrograma", source = "curso.asignatura.programa.nombre")
	@Mapping(target = "grupo", source = "curso.grupo")
	@Mapping(target = "periodoAcademicoAnio", source = "curso.periodoAcademico.anio")
	@Mapping(target = "OIDAsignatura", source = "curso.asignatura.OID")
	@Mapping(target = "semestre", source = "curso.asignatura.semestre")
	@Mapping(target = "horas", source = "curso.asignatura.horasSemana")
	CursoOutDTO toCursoOutDTO(Curso curso);

	@Mapping(target = "asignatura", expression = "java(new Asignatura(cursoInDTO.getIdAsignatura()))")
	@Mapping(target = "docentes", ignore = true)
	@Mapping(target = "horarios", ignore = true)
	Curso toCurso(CursoInDTO cursoInDTO);

	List<CursoOutDTO> toLstCursoOutDTO(List<Curso> lstCurso);
}