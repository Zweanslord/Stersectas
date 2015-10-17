package stersectas.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

import stersectas.documentation.HibernateConstructor;

@MappedSuperclass
@Embeddable
public abstract class Id implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MINIMUM_LENGTH = 10;

	@Column(nullable = false, unique = true)
	private String id;

	@HibernateConstructor
	protected Id() {
	}

	public Id(String id) {
		if (id.trim().length() < MINIMUM_LENGTH) {
			throw new IllegalArgumentException(String.format("Id must be at least %s long.", MINIMUM_LENGTH));
		}
		this.id = id;
	}

	public String id() {
		return id;
	}

	@Override
	public boolean equals(Object object) {
		final boolean result;
		if (object instanceof Id) {
			final Id other = (Id) object;
			result = id.equals(other.id);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}