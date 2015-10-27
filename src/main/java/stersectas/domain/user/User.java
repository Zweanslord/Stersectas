package stersectas.domain.user;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import stersectas.documentation.HibernateConstructor;

@Entity
public class User {

	@EmbeddedId
	private UserId userId;

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

	@HibernateConstructor
	protected User() {
	}

	public User(UserId userId, String username, String email, String password) {
		this.userId = userId;
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

	public UserId getUserId() {
		return userId;
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

	public void setPassword(String password) {
		if (isDisabled()) {
			throw new IllegalStateException("Can't demote disabled user");
		}
		this.password = password;
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