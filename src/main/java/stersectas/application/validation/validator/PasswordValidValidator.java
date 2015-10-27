package stersectas.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import stersectas.application.security.SecurityService;
import stersectas.application.user.UpdateUserPassword;
import stersectas.application.validation.PasswordValid;
import stersectas.domain.user.User;

public class PasswordValidValidator implements ConstraintValidator<PasswordValid, UpdateUserPassword> {

	private final SecurityService securityService;
	private final PasswordEncoder encoder;

	@Autowired
	public PasswordValidValidator(SecurityService securityService, PasswordEncoder encoder) {
		this.securityService = securityService;
		this.encoder = encoder;
	}

	@Override
	public void initialize(PasswordValid constraintAnnotation) {
	}

	@Override
	public boolean isValid(UpdateUserPassword updateUserPassword, ConstraintValidatorContext context) {
		User user = securityService.currentUser();
		if (encoder.matches(updateUserPassword.getCurrentPassword(), user.getPassword())) {
			return true;
		} else {
			context
				.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("currentPassword")
						.addConstraintViolation();
			return false;
		}
	}
}
