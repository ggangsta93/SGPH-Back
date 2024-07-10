package co.edu.unicauca.sgph.usuario.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.UsuarioInDTO;

@Component
public class ExisteIdPersonaUsuarioValidation
		implements ConstraintValidator<ExisteIdPersonaUsuario, UsuarioInDTO> {

	@Autowired
	private GestionarUsuarioGatewayIntPort gestionarUsuarioGatewayIntPort;

	@Override
	public boolean isValid(UsuarioInDTO usuarioInDTO, ConstraintValidatorContext context) {
		return !this.gestionarUsuarioGatewayIntPort.existeUsuarioPorIdPersona(usuarioInDTO.getIdPersona(), usuarioInDTO.getIdUsuario());
	}
}
