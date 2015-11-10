package stersectas.domain.game;

import javax.persistence.Embeddable;

import lombok.Value;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

/**
 * Total number of {@link Player}s which the {@link Master} can accommodate in a specific {@link RecruitingGame}.
 */
@Embeddable
@Value
@Accessors(fluent = true)
public class MaximumPlayers {

	static int MIN_OF_PLAYERS = 1,
			ABSOLUTE_MAX_OF_PLAYERS = 8;

	int maximumPlayers;

	@HibernateConstructor
	private MaximumPlayers() {
		maximumPlayers = 4;
	}

	public MaximumPlayers(int maximumOfPlayers) {
		if (maximumOfPlayers > ABSOLUTE_MAX_OF_PLAYERS) {
			throw new IllegalArgumentException(
					String.format("Player count %s exceeds absolute maximum of %s", maximumOfPlayers,
							ABSOLUTE_MAX_OF_PLAYERS));
		} else if (maximumOfPlayers < MIN_OF_PLAYERS) {
			throw new IllegalArgumentException(
					String.format("Player count %s below minimum of %s", maximumOfPlayers, MIN_OF_PLAYERS));
		}
		maximumPlayers = maximumOfPlayers;
	}

	public static int absoluteMaximumOfPlayers() {
		return ABSOLUTE_MAX_OF_PLAYERS;
	}

}