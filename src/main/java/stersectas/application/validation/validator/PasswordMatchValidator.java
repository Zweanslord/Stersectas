package stersectas.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import stersectas.application.user.PasswordConfirmation;
import stersectas.application.validation.PasswordsMatch;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatch, PasswordConfirmation> {

	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(PasswordConfirmation passwordConfirmation, ConstraintValidatorContext context) {
		if (passwordConfirmation.getPassword().equals(passwordConfirmation.getPasswordConfirmation())) {
			return true;
		} else {
			context
				.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("passwordConfirmation")
						.addConstraintViolation();
			return false;
		}
	}
}
