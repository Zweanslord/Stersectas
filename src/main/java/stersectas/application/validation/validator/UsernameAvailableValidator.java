package stersectas.application.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import stersectas.application.validation.UsernameAvailable;
import stersectas.domain.user.UserRepository;

public class UsernameAvailableValidator implements ConstraintValidator<UsernameAvailable, String> {

	private final UserRepository userRepository;

	@Autowired
	public UsernameAvailableValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void initialize(UsernameAvailable constraintAnnotation) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userRepository.findByUsername(username).isPresent();
	}

}
