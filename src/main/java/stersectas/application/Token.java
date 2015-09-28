package stersectas.application;

import javax.persistence.Embeddable;

@Embeddable
public class Token {

	private String token;

	protected Token() {
	}

	public Token(String token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null
				|| !(object instanceof Token)) {
			return false;
		}

		Token other = (Token) object;
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
