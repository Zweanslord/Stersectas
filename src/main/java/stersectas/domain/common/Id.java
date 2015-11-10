package stersectas.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

@MappedSuperclass
@Embeddable
@EqualsAndHashCode
@Accessors(fluent = true)
public abstract class Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MINIMUM_LENGTH = 10;

	@Column(nullable = false, unique = true)
	@Getter private String id;

	@HibernateConstructor
	protected Id() {
	}

	public Id(String id) {
		if (id.trim().length() < MINIMUM_LENGTH) {
			throw new IllegalArgumentException(String.format("Id must be at least %s long.", MINIMUM_LENGTH));
		}
		this.id = id;
	}

}