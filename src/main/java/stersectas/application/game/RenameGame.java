package stersectas.application.game;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RenameGame {

	@NotNull
	private String gameId;

	// add validation for unique name
	@Size(min = 1, max = 30)
	private String name;

	public RenameGame() {
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}