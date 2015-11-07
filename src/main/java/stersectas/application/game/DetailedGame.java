package stersectas.application.game;

import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

public class DetailedGame {

	private final String gameId;
	private final String name;
	private final String description;
	private final int maximumPlayers;
	private final String masterName;

	DetailedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		description = game.description().description();
		maximumPlayers = game.maximumPlayers().maximum();
		this.masterName = masterName.name();
	}

	public String getGameId() {
		return gameId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getMaximumPlayers() {
		return maximumPlayers;
	}

	public String getMasterName() {
		return masterName;
	}

}