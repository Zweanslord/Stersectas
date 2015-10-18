package stersectas.domain.game;

import javax.persistence.Entity;

import stersectas.domain.user.UserId;

@Entity
public class ArchivedGame extends Game {

	ArchivedGame(
			Name name,
			Description description,
			MaximumPlayers maximumPlayers,
			UserId masterId) {
		super(name,
				description,
				maximumPlayers,
				masterId);
	}

}