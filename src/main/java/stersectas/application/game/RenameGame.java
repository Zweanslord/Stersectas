package stersectas.application.game;

import javax.validation.constraints.Size;

import stersectas.application.validation.ExtendedValidations;

public class RenameGame {

	@Size(min = 1)
	private String gameId;

	@Size(min = 1, max = 30)
	@GameNameAvailable(groups = ExtendedValidations.class)
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