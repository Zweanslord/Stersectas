package stersectas.application.game;

public class RenameGame {

	private String gameId;
	private String name;

	public RenameGame() {
	}

	public RenameGame(
			String gameId,
			String name) {
		this.gameId = gameId;
		this.name = name;
	}

	public String getGameId() {
		return gameId;
	}


	public String getName() {
		return name;
	}

}