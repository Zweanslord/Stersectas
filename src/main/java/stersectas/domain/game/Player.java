package stersectas.domain.game;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.user.UserId;

/**
 * Non-organizing participant in a {@link Game}.
 */
@Embeddable
public class Player {

	@Embedded
	private UserId userId;

	@Embedded
	private Name name;

	@HibernateConstructor
	protected Player() {
	}

	Player(UserId userId, Name name) {
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
		if (object instanceof Player) {
			final Player other = (Player) object;
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
