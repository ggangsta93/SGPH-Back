package co.edu.unicauca.sgph.docente.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteInDTO;

@Component
public class ExisteIdPersonaDocenteValidation
		implements ConstraintValidator<ExisteIdPersonaDocente, DocenteInDTO> {

	@Autowired
	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;

	@Override
	public boolean isValid(DocenteInDTO docenteInDTO, ConstraintValidatorContext context) {
		return !this.gestionarDocenteGatewayIntPort.existeDocentePorIdPersona(docenteInDTO.getIdPersona(), docenteInDTO.getIdDocente());
	}
}
