package stersectas.application.game;

import javax.validation.constraints.Size;

public class ChangeGameDescription {

	@Size(min = 1)
	private String gameId;

	@Size(min = 1, max = 1000)
	private String description;

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}