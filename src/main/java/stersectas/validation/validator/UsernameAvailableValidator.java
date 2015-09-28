package stersectas.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import stersectas.repositories.UserRepository;
import stersectas.validation.UsernameAvailable;

public class UsernameAvailableValidator implements ConstraintValidator<UsernameAvailable, String> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(UsernameAvailable constraintAnnotation) {
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userRepository.findByUsername(username).isPresent();
	}

}
