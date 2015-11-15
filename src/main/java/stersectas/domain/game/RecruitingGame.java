package stersectas.domain.game;

import javax.persistence.Entity;

import stersectas.documentation.HibernateConstructor;

/**
 * A story where multiple participants can play the events and adventure together.
 * A {@link Master} organizes the game and recruits {@link Player}s.
 */
@Entity
public class RecruitingGame extends Game {

	@HibernateConstructor
	protected RecruitingGame() {
	}

	// TODO add players
	// @ElementCollection
	// @CollectionTable
	// private Set<UserId> players;

	public RecruitingGame(
			GameId gameId,
			Name name,
			Description description,
			MaximumPlayers maximumPlayers,
			GamerId masterId) {
		super(gameId,
				name,
				description,
				maximumPlayers,
				masterId);
		// players = new HashSet<>();
	}

	@Override
	public void adjustMaximumOfPlayers(MaximumPlayers maximumPlayers) {
		super.adjustMaximumOfPlayers(maximumPlayers);
	}

	@Override
	public void rename(Name name) {
		super.rename(name);
	}

	@Override
	public void changeDescription(Description description) {
		super.changeDescription(description);
	}

	public void start() {
		// TODO implement and add player requirement
		throw new UnsupportedOperationException();
	}

	@Override
	public ArchivedGame archive() {
		return new ArchivedGame(
				gameId(),
				name(),
				description(),
				maximumPlayers(),
				masterId()
				//players()
				);
	}

}