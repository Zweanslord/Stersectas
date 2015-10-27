package stersectas.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import stersectas.application.user.UpdateUserPassword;
import stersectas.application.user.UserService;
import stersectas.application.validation.PasswordValid;
import stersectas.domain.user.User;

public class PasswordValidValidator implements ConstraintValidator<PasswordValid, UpdateUserPassword> {

	private final UserService userService;
	private final PasswordEncoder encoder;

	@Autowired
	public PasswordValidValidator(UserService userService, PasswordEncoder encoder) {
		this.userService = userService;
		this.encoder = encoder;
	}

	@Override
	public void initialize(PasswordValid constraintAnnotation) {
	}

	@Override
	public boolean isValid(UpdateUserPassword updateUserPassword, ConstraintValidatorContext context) {
		User user = userService.findByUsername(updateUserPassword.getUsername());

		return encoder.matches(user.getPassword(), updateUserPassword.getCurrentPassword());
	}
}
