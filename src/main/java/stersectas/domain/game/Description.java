package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Value;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

@Embeddable
@Value
@Accessors(fluent = true)
public class Description {

	static int MAXIMUM_LENGTH = 1000;

	@Column(nullable = false, length = MAXIMUM_LENGTH)
	String description;

	@HibernateConstructor
	private Description() {
		description = "";
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

}