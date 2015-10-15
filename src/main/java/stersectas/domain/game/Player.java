package stersectas.domain.game;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;

/**
 * Non-organizing participant in a {@link Game}.
 */
@Embeddable
public class Player {

	// TODO same question as Master, should this be a user's name or id?
	@Column(nullable = false)
	private String player;

	@HibernateConstructor
	Player() {
	}

	Player(String player) {
		if (player == null || player.trim().isEmpty()) {
			throw new IllegalArgumentException("Name can not be empty.");
		}
		this.player = player;
	}

	public String name() {
		return player;
	}

}
