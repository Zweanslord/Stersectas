package stersectas.domain.game;

import javax.persistence.Entity;

import stersectas.documentation.HibernateConstructor;

@Entity
public class ArchivedGame extends Game {

	@HibernateConstructor
	protected ArchivedGame() {
	}

	ArchivedGame(
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
	}

	@Override
	public ArchivedGame archive() {
		return new ArchivedGame(
				gameId(),
				name(),
				description(),
				maximumPlayers(),
				masterId());
	}

}