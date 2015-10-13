package stersectas.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Size(min = 3, max = 30)
	@Column(nullable = false, unique = true)
	private String username;

	@Email
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean enabled;

	private Role role;

	protected User() {
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		enabled = false;
		role = Role.USER;
	}

	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}

	public void promoteToAdministrator() {
		if (isDisabled()) {
			throw new IllegalStateException("Can't promote disabled user to administrator.");
		}
		role = Role.ADMIN;
	}

	public void demoteToUser() {
		if (isDisabled()) {
			throw new IllegalStateException("Can't demote disabled user");
		}
		role = Role.USER;
	}

	public boolean isAdministrator() {
		return role == Role.ADMIN;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isDisabled() {
		return !enabled;
	}

	public Role getRole() {
		return role;
	}

}