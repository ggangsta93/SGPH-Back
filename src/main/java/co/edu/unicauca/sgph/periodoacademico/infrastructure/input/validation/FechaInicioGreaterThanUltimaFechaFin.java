package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaInicioGreaterThanUltimaFechaFinValidation.class)
public @interface FechaInicioGreaterThanUltimaFechaFin {

	String message() default "{periodo.academico.fecha.inicio.greater.than.ultima.fecha.fin}";
	String messageKey() default "periodo.academico.fecha.inicio.greater.than.ultima.fecha.fin ";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
