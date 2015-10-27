package stersectas.application.user;

import javax.validation.constraints.Size;

import stersectas.application.validation.PasswordValid;
import stersectas.application.validation.PasswordsMatch;

@PasswordValid
@PasswordsMatch
public class UpdateUserPassword implements PasswordConfirmation {

	private String username;

	private String currentPassword;

	@Size(min = 8, max = 100)
	private String password;

	private String passwordConfirmation;

	public UpdateUserPassword() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

}
