package co.edu.unicauca.sgph.usuario.infrastructure.input.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsAtLeastOneProgramForPlanificadorRoleValidation.class)
public @interface ExistsAtLeastOneProgramForPlanificadorRole {

	String message() default "{usuario.exists.at.least.one.programa.for.planificador.rol}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
