package stersectas.application.game;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GameNameAvailableValidator.class)
@Documented
public @interface GameNameAvailable {

	String message() default "Game name already in use";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}