package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;

@Component
public class FechaFinGreaterThanFechaInicioValidation
		implements ConstraintValidator<FechaFinGreaterThanFechaInicio, PeriodoAcademicoInDTO> {

	@Override
	public boolean isValid(PeriodoAcademicoInDTO periodoAcademicoInDTO, ConstraintValidatorContext context) {
		if (Objects.nonNull(periodoAcademicoInDTO.getFechaInicioPeriodo())
				&& Objects.nonNull(periodoAcademicoInDTO.getFechaFinPeriodo())
				&& periodoAcademicoInDTO.getFechaFinPeriodo().after(periodoAcademicoInDTO.getFechaInicioPeriodo())) {

			//Fecha de inicio + 4 meses
			LocalDate fechaInicio = periodoAcademicoInDTO.getFechaInicioPeriodo().toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDate().plusMonths(4);
			
			LocalDate fechaFin = periodoAcademicoInDTO.getFechaFinPeriodo().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			return fechaFin.isAfter(fechaInicio);
		}
		return Boolean.FALSE;
	}
}
