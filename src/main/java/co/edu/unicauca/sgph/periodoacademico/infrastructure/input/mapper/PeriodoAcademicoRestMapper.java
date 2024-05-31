package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;

@Mapper(componentModel = "spring")
public interface PeriodoAcademicoRestMapper {

	PeriodoAcademicoOutDTO toPeriodoAcademicoOutDTO(PeriodoAcademico periodoAcademico);

	PeriodoAcademico toPeriodoAcademico(PeriodoAcademicoInDTO periodoAcademicoInDTO);
	
	List<PeriodoAcademicoOutDTO> toLstPeriodoAcademicoOutDTO(List<PeriodoAcademico> lstPeriodoAcademico);
}