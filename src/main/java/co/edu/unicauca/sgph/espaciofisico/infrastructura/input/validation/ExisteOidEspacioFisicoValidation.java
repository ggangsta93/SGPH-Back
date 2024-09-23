package co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;

@Component
public class ExisteOidEspacioFisicoValidation implements ConstraintValidator<ExisteOidEspacioFisico, EspacioFisicoInDTO>{

	@Autowired
	private GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort;
	
	@Override
	public boolean isValid(EspacioFisicoInDTO value, ConstraintValidatorContext context) {
		return !this.gestionarEspacioFisicoGatewayIntPort.existsEspacioFisicoByOid(value.getOID());
	}
}
