package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;

/**
 * Organizer and manager of a {@link Game}.
 */
@Embeddable
public class Master {

	// TODO String used here, should this really be (user)name or an id?
	// We don't want the full user, to stay only loosely coupled.
	@Column(nullable = false)
	private String master;

	@HibernateConstructor
	protected Master() {
	}

	Master(String master) {
		if (master == null || master.trim().isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty.");
		}
		this.master = master;
	}

	public String name() {
		return master;
	}

}