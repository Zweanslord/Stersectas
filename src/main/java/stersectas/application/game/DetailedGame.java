package stersectas.application.game;

import lombok.Getter;
import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

/**
 * A Data Transfer Object (DTO) used for the view to show a a game with all its details.
 */
public class DetailedGame {

	@Getter private final String gameId;
	@Getter private final String name;
	@Getter private final String description;
	@Getter private final int maximumPlayers;
	@Getter private final String masterName;

	DetailedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		description = game.description().description();
		maximumPlayers = game.maximumPlayers().maximum();
		this.masterName = masterName.name();
	}

}