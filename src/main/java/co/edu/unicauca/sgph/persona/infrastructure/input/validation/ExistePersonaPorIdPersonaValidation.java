package co.edu.unicauca.sgph.persona.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;

@Component
public class ExistePersonaPorIdPersonaValidation
		implements ConstraintValidator<ExistePersonaPorIdPersona, Long> {

	@Autowired
	private GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort;

	@Override
	public boolean isValid(Long idPersona, ConstraintValidatorContext context) {
		return this.gestionarPersonaGatewayIntPort.existePersonaPorIdPersona(idPersona);
	}
}
