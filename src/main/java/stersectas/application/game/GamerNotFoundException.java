package stersectas.application.game;

public class GamerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GamerNotFoundException() {
		super("Gamer was not found.");
	}
}
