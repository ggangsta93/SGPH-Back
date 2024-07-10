package co.edu.unicauca.sgph.persona.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.persona.infrastructure.input.DTORequest.PersonaInDTO;

@Component
public class ExistePersonaPorIdentificacionValidation
		implements ConstraintValidator<ExistePersonaPorIdentificacion, PersonaInDTO> {

	@Autowired
	private GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort;

	@Override
	public boolean isValid(PersonaInDTO personaInDTO, ConstraintValidatorContext context) {
		return !this.gestionarPersonaGatewayIntPort.existePersonaPorIdentificacion(
				personaInDTO.getIdTipoIdentificacion(), personaInDTO.getNumeroIdentificacion(),
				personaInDTO.getIdPersona());
	}
}
