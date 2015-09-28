package stersectas.application;

import java.time.LocalDate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import stersectas.domain.User;

@Entity
public class VerificationToken {

	private static final long EXPIRATION_DAYS = 1;

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Token token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private LocalDate expirationDate;

	protected VerificationToken() {
	}

	public VerificationToken(Token token, User user, LocalDate today) {
		this.token = token;
		this.user = user;
		expirationDate = today.plusDays(EXPIRATION_DAYS);
	}

	public Long getId() {
		return id;
	}

	public Token getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public boolean isExpired(LocalDate today) {
		return today.isAfter(expirationDate);
	}
}
