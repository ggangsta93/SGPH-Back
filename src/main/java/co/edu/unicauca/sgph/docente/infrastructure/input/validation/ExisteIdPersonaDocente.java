package co.edu.unicauca.sgph.docente.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteIdPersonaDocenteValidation.class)
public @interface ExisteIdPersonaDocente {
	String message() default "{docente.existe.docente.por.idPersona}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
