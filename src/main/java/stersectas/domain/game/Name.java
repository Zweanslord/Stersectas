package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import stersectas.documentation.HibernateConstructor;

@Embeddable
public class Name {

	private static final int MAXIMUM_LENGTH = 30;

	@Size(max = MAXIMUM_LENGTH)
	@Column(nullable = false)
	private String name;

	@HibernateConstructor
	Name() {

	}

	public Name(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty.");
		} else if (name.length() > MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(String.format("Name exceeds maximum length of %s.", MAXIMUM_LENGTH));
		}
		this.name = name;
	}

	public String name() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		final boolean result;
		if (object instanceof Name) {
			final Name other = (Name) object;
			result = name.equals(other.name);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
