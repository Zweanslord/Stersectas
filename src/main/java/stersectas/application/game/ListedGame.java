package stersectas.application.game;

import lombok.Getter;
import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

/**
 * A Data Transfer Object (DTO) used for the view to show a list of games.
 */
public class ListedGame {

	@Getter private final String gameId;
	@Getter private final String name;
	@Getter private final int maximumPlayers;
	@Getter private final String masterName;

	ListedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		maximumPlayers = game.maximumPlayers().maximum();
		this.masterName = masterName.name();
	}

}