package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;

@Component
public class FechaInicioGreaterThanUltimaFechaFinValidation
		implements ConstraintValidator<FechaInicioGreaterThanUltimaFechaFin, PeriodoAcademicoInDTO> {

	@Autowired
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;

	@Override
	public boolean isValid(PeriodoAcademicoInDTO periodoAcademicoInDTO, ConstraintValidatorContext context) {
		if (Objects.nonNull(periodoAcademicoInDTO.getFechaInicioPeriodo())) {
			return this.gestionarPeriodoAcademicoGatewayIntPort
					.esFechaInicioGreaterThanUltimaFechaFin(periodoAcademicoInDTO.getFechaInicioPeriodo());
		}
		return Boolean.FALSE;
	}
}
