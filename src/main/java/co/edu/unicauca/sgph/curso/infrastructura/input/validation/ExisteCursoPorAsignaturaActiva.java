package co.edu.unicauca.sgph.curso.infrastructura.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteCursoPorAsignaturaActivaValidation.class)
public @interface ExisteCursoPorAsignaturaActiva {
	String message() default "{curso.exists.by.asignatura.activa}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
