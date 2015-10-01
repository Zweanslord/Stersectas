package stersectas.application;

import java.time.LocalDateTime;

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
	private RandomToken token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private LocalDateTime expirationTime;

	/** Hibernate constructor */
	protected VerificationToken() {
	}

	private VerificationToken(RandomToken token, User user, LocalDateTime now) {
		this.token = token;
		this.user = user;
		expirationTime = now.plusDays(EXPIRATION_DAYS);
	}

	public static VerificationToken create(User user, LocalDateTime now) {
		return new VerificationToken(RandomToken.create(), user, now);
	}

	public String tokenString() {
		return token.toString();
	}

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getExpirationDate() {
		return expirationTime;
	}

	public boolean isExpired(LocalDateTime now) {
		return now.isAfter(expirationTime);
	}
}
