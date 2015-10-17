package stersectas.domain.game;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.user.UserId;

/**
 * Organizer and manager of a {@link Game}.
 */
@Embeddable
public class Master {

	@Embedded
	private UserId userId;

	@Embedded
	private Name name;

	@HibernateConstructor
	protected Master() {
	}

	Master(UserId userId, Name name) {
		this.userId = userId;
		this.name = name;
	}

	public UserId userId() {
		return userId;
	}

	public Name name() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		final boolean result;
		if (object instanceof Master) {
			final Master other = (Master) object;
			result = userId.equals(other.userId)
					&& name.equals(other.name);
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return userId.hashCode() + name.hashCode();
	}

}