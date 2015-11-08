package stersectas.application.game;

public class ChangeGameDescription {

	private final String gameId;
	private final String description;

	public ChangeGameDescription(String gameId, String description) {
		this.gameId = gameId;
		this.description = description;
	}

	public String getGameId() {
		return gameId;
	}

	public String getDescription() {
		return description;
	}

}