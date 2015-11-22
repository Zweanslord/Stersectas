package stersectas.application.game;

import lombok.Getter;
import stersectas.domain.game.Game;
import stersectas.domain.game.Name;

/**
 * A Data Transfer Object (DTO) used for the view to show a a game with all its details.
 */
@Getter
public class DetailedGame {

	private final String gameId;
	private final String name;
	private final String description;
	private final int maximumPlayers;
	private final String masterName;
	private final boolean archived;

	DetailedGame(Game game, Name masterName) {
		gameId = game.gameId().id();
		name = game.name().name();
		description = game.description().description();
		maximumPlayers = game.maximumPlayers().maximumPlayers();
		this.masterName = masterName.name();
		archived = game.isArchived();

	}

}