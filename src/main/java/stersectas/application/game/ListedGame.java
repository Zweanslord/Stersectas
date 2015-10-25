package stersectas.application.game;

import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

/**
 * A Data Transfer Object (DTO) used for the view to show a list of games.
 */
public class ListedGame {

	private String gameId;
	private String name;
	private int maximumPlayers;
	private String masterName;

	ListedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		maximumPlayers = game.maximumPlayers().maximum();
		this.masterName = masterName.name();
	}

	public String getGameId() {
		return gameId;
	}

	public String getName() {
		return name;
	}

	public int getMaximumPlayers() {
		return maximumPlayers;
	}

	public String getMasterName() {
		return masterName;
	}

}