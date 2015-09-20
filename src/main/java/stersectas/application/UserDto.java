package stersectas.application;

import javax.validation.constraints.Size;

import stersectas.validation.PasswordsMatch;

@PasswordsMatch
public class UserDto {

	@Size(min = 8, max = 30)
	private String username;

	@Size(min = 8, max = 100)
	private String password;
	
	@Size(min = 8, max = 100)
	private String passwordConfirmation;
	
	public UserDto() {
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
}
