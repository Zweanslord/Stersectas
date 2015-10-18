package stersectas.domain.game;

import javax.persistence.Entity;

import stersectas.documentation.HibernateConstructor;
import stersectas.domain.user.UserId;

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
			UserId masterId) {
		super(gameId,
				name,
				description,
				maximumPlayers,
				masterId);
	}

}