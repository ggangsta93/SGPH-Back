package co.edu.unicauca.sgph.agrupador.infrastructure.input.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AgrupadorEspacioFisicoInDTO;

@Component
public class ExisteNombreAgrupadorValidation
		implements ConstraintValidator<ExisteNombreAgrupador, AgrupadorEspacioFisicoInDTO> {

	@Autowired
	private GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort;

	@Override
	public boolean isValid(AgrupadorEspacioFisicoInDTO agrupadorEspacioFisicoInDTO,
			ConstraintValidatorContext context) {
		return !this.gestionarAgrupadorEspacioFisicoGatewayIntPort.existeAgrupadorPorNombreAgrupador(
				agrupadorEspacioFisicoInDTO.getNombre().trim(), agrupadorEspacioFisicoInDTO.getIdAgrupadorEspacioFisico());
	}
}
