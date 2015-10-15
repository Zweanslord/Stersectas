package stersectas.domain.game;

import javax.persistence.Embeddable;

import stersectas.documentation.HibernateConstructor;

/**
 * Total number of {@link Player}s which the {@link Master} can accommodate in a specific {@link Game}.
 */
@Embeddable
public class MaximumPlayers {

	private static int MIN_OF_PLAYERS = 1,
			ABSOLUTE_MAX_OF_PLAYERS = 8;

	private int maximumPlayers;

	@HibernateConstructor
	MaximumPlayers() {
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

	public int maximum() {
		return maximumPlayers;
	}

	@Override
	public boolean equals(Object object) {
		final boolean result;
		if (object instanceof MaximumPlayers) {
			final MaximumPlayers other = (MaximumPlayers) object;
			result = maximumPlayers == other.maximumPlayers;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(maximumPlayers);
	}
}