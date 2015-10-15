package stersectas.domain.game;

/**
 * The different phases of a {@link Game}, ordered by their most logical chronological order.
 *
 */
public enum GameState {
	PREPARING,
	RECRUITING,
	RUNNING,
	FINISHED,
	ARCHIVED;

	public boolean afterStart() {
		return ordinal() >= RUNNING.ordinal();
	}

	public boolean stopped() {
		return ordinal() > RUNNING.ordinal();
	}

}