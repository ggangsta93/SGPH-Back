package co.edu.unicauca.sgph.curso.infrastructura.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.infrastructure.input.DTORequest.CursoInDTO;

@Component
public class ExisteCursoPorAsignaturaActivaValidation implements ConstraintValidator<ExisteCursoPorAsignaturaActiva, CursoInDTO>{

	@Autowired
	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	
	@Override
	public boolean isValid(CursoInDTO value, ConstraintValidatorContext context) {
		return !this.gestionarCursoGatewayIntPort.existsCursoByAsignaturaActiva(value.getIdAsignatura());
	}

}
