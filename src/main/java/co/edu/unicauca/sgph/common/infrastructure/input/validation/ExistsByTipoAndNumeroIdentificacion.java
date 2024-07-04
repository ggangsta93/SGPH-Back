package co.edu.unicauca.sgph.common.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByTipoAndNumeroIdentificacionValidation.class)
public @interface ExistsByTipoAndNumeroIdentificacion {
	String message() default "{persona.exists.by.tipo.and.numero.identificacion}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
