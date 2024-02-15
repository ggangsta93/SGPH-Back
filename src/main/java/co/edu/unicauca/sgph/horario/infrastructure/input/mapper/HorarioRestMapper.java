package co.edu.unicauca.sgph.horario.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.HorarioInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.HorarioOutDTO;

@Mapper(componentModel = "spring")
public interface HorarioRestMapper {

	@Mapping(target = "idCurso", expression = "java(horario.getCurso().getIdCurso())")
	HorarioOutDTO toHorarioOutDTO(Horario horario);

	@Mapping(target = "curso", expression = "java(new Curso(horarioInDTO.getIdCurso()))")
	@Mapping(target = "aulas", ignore = true)
	Horario toHorario(HorarioInDTO horarioInDTO);

	List<HorarioOutDTO> toLstHorarioOutDTO(List<Horario> lstHorario);
}