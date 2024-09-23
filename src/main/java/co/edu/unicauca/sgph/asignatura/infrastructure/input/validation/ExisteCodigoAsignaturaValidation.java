package co.edu.unicauca.sgph.asignatura.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;

@Component
public class ExisteCodigoAsignaturaValidation implements ConstraintValidator<ExisteCodigoAsignatura, AsignaturaInDTO>{

	@Autowired
	private GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort;
	
	@Override
	public boolean isValid(AsignaturaInDTO value, ConstraintValidatorContext context) {
		return !this.gestionarAsignaturaGatewayIntPort.existeCodigoAsignatura(value.getCodigoAsignatura());
	}
}
