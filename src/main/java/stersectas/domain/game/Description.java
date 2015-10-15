package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import stersectas.documentation.HibernateConstructor;

@Embeddable
public class Description {

	private static final int MAXIMUM_LENGTH = 1000;

	@Size(max = MAXIMUM_LENGTH)
	@Column(nullable = false)
	private String description;

	@HibernateConstructor
	Description() {
	}

	public Description(String description) {
		if (description.trim().isEmpty()) {
			throw new IllegalArgumentException("Description can not be empty.");
		} else if (description.length() > MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(
					String.format("Description exceeds maximum length of %s.", MAXIMUM_LENGTH));
		}
		this.description = description;
	}

	public String description() {
		return description;
	}

	@Override
	public boolean equals(Object object) {
		final boolean result;
		if (object instanceof Description) {
			final Description other = (Description) object;
			result = description.equals(other.description);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return description.hashCode();
	}

}
