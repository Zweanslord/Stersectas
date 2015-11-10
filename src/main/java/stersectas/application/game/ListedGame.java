package stersectas.application.game;

import lombok.Getter;
import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

/**
 * A Data Transfer Object (DTO) used for the view to show a list of games.
 */
@Getter
public class ListedGame {

	private final String gameId;
	private final String name;
	private final int maximumPlayers;
	private final String masterName;

	ListedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		maximumPlayers = game.maximumPlayers().maximumPlayers();
		this.masterName = masterName.name();
	}

}