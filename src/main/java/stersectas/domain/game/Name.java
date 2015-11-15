package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Value;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

@Embeddable
@Value
@Accessors(fluent = true)
public class Name {

	static int MAXIMUM_LENGTH = 30;

	@Size(max = MAXIMUM_LENGTH)
	@Column(nullable = false)
	String name;

	@HibernateConstructor
	private Name() {
		name = "";
	}

	public Name(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty.");
		} else if (name.length() > MAXIMUM_LENGTH) {
			throw new IllegalArgumentException(String.format("Name exceeds maximum length of %s.", MAXIMUM_LENGTH));
		}
		this.name = name;
	}

}
