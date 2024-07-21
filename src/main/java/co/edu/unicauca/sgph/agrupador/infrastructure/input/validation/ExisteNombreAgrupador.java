package co.edu.unicauca.sgph.agrupador.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteNombreAgrupadorValidation.class)
public @interface ExisteNombreAgrupador {
	String message() default "{agrupador.existe.nombreAgrupador}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
