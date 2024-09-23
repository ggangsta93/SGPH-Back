package co.edu.unicauca.sgph.espaciofisico.infrastructura.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteNombreEspacioFisicoValidation.class)
public @interface ExisteNombreEspacioFisico {
	String message() default "{espaciofisico.exists.by.nombre}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
