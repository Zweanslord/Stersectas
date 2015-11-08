package stersectas.application.game;

public class GameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GameNotFoundException() {
		super("Game was not found.");
	}

}
