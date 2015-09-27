package stersectas.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import stersectas.application.RegisterUser;
import stersectas.validation.PasswordsMatch;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterUser> {

	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(RegisterUser user, ConstraintValidatorContext context) {
		if (!user.getPassword().equals(user.getPasswordConfirmation())) {
			context
				.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("passwordConfirmation")
						.addConstraintViolation();
			return false;
		} else {
			return true;
		}
	}
}
