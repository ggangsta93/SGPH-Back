package co.edu.unicauca.sgph.docente.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsByCodigoValidation.class)
public @interface ExistsByCodigo {

	String message() default "{persona.exists.by.nombreUsuario}";
	String messageKey() default "persona.exists.by.nombreUsuario";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
