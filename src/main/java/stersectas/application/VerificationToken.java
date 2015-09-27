package stersectas.application;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import stersectas.domain.User;

@Entity
public class VerificationToken {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	private Date expirationDate;
	
	protected VerificationToken() {
	}
	
	public VerificationToken(String token, User user) {
		this.token = token;
		this.user = user;
		this.expirationDate = calculateExpirationDate();
	}
	
	private Date calculateExpirationDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, 60 * 24);
		return new Date(cal.getTime().getTime());
	}
	
	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
