package co.edu.unicauca.sgph.usuario.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExisteNombreUsuarioValidation.class)
public @interface ExisteNombreUsuario {
	String message() default "{usuario.existe.nombreUsuario}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
