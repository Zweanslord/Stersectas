package stersectas.application.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import stersectas.application.validation.validator.UsernameAvailableValidator;

@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameAvailableValidator.class)
@Documented
public @interface UsernameAvailable {
	
	String message() default "Username already in use";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
