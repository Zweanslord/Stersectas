package stersectas.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;

import stersectas.application.UserDto;
import stersectas.validation.PasswordsMatch;

public class PasswordMatchValidator implements ConstraintValidator<PasswordsMatch, UserDto> {

	@Override
	public void initialize(PasswordsMatch constraintAnnotation) {
	}

	@Override
	public boolean isValid(UserDto user, ConstraintValidatorContext context) {
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
