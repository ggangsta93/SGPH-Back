package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.mapper;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;

@Mapper(componentModel = "spring")
public interface PeriodoAcademicoRestMapper {

	PeriodoAcademicoOutDTO toPeriodoAcademicoOutDTO(PeriodoAcademico periodoAcademico);

	PeriodoAcademico toPeriodoAcademico(PeriodoAcademicoInDTO periodoAcademicoInDTO);

}