package co.edu.unicauca.sgph.periodoacademico.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByAnioAndPeriodoValidation.class)
public @interface ExistsByAnioAndPeriodo {
	String message() default "{periodo.academico.exists.by.anio.and.periodo}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
