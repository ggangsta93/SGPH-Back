package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

	
	/*public boolean isValid(PeriodoAcademicoInDTO periodoAcademicoInDTO, ConstraintValidatorContext context) {
		if (Objects.nonNull(periodoAcademicoInDTO.getFechaInicioPeriodo())) {
			return this.gestionarPeriodoAcademicoGatewayIntPort
					.esFechaInicioGreaterThanUltimaFechaFin(periodoAcademicoInDTO.getFechaInicioPeriodo());
		}
		return Boolean.FALSE;
	}*/
	@Override
    public boolean isValid(PeriodoAcademicoInDTO periodoAcademicoInDTO, ConstraintValidatorContext context) {
        if (Objects.nonNull(periodoAcademicoInDTO.getFechaInicioPeriodo())) {
            Date fechaFin = this.gestionarPeriodoAcademicoGatewayIntPort
                    .esFechaInicioGreaterThanUltimaFechaFin(periodoAcademicoInDTO.getFechaInicioPeriodo());

            if (Objects.nonNull(fechaFin)) {
            	
                SimpleDateFormat formato = new SimpleDateFormat("dd/MMMM/yyyy", new Locale("es", "ES"));
                String fechaFinFormateada = formato.format(fechaFin);
                                
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "La fecha inicio debe ser mayor que la última fecha fin. "
                                + fechaFinFormateada)
                        .addConstraintViolation();
            }

            return Objects.isNull(fechaFin);
        }
        return Boolean.FALSE;
    }
}
