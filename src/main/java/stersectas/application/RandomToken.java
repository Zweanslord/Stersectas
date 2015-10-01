package stersectas.application;

import java.util.UUID;

import javax.persistence.Embeddable;

@Embeddable
public class RandomToken {

	private String token;

	protected RandomToken() {
	}

	protected RandomToken(String token) {
		this.token = token;
	}

	public static RandomToken create() {
		return new RandomToken(UUID.randomUUID().toString());
	}

	public static RandomToken from(String token) {
		return new RandomToken(token);
	}

	@Override
	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof RandomToken)) {
			return false;
		}

		RandomToken other = (RandomToken) object;
		return token.equals(other.token);
	}

	@Override
	public int hashCode() {
		return token.hashCode();
	}

	@Override
	public String toString() {
		return token;
	}

}
