package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.PeriodoAcademicoInDTO;

@Component
public class ExistsByAnioAndPeriodoValidation
		implements ConstraintValidator<ExistsByAnioAndPeriodo, PeriodoAcademicoInDTO> {

	@Autowired
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;

	@Override
	public boolean isValid(PeriodoAcademicoInDTO periodoAcademicoInDTO, ConstraintValidatorContext context) {
		PeriodoAcademico periodoAcademico = this.gestionarPeriodoAcademicoGatewayIntPort
				.existsByAnioAndPeriodo(periodoAcademicoInDTO.getAnio(), periodoAcademicoInDTO.getPeriodo());
		Boolean esValido;
		if (Objects.isNull(periodoAcademico)) {
			esValido = Boolean.TRUE;
		} else if (Objects.nonNull(periodoAcademicoInDTO.getIdPeriodoAcademico())
				&& periodoAcademicoInDTO.getIdPeriodoAcademico().equals(periodoAcademico.getIdPeriodoAcademico())) {
			esValido = Boolean.TRUE;
		} else {
			esValido = Boolean.FALSE;
		}
		return esValido;
	}
}
