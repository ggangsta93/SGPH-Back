package co.edu.unicauca.sgph.curso.infrastructura.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteCursoConMismoGrupoValidation.class)
public @interface ExisteCursoConMismoGrupo {
	String message() default "{curso.exists.by.grupo}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
