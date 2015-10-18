package stersectas.domain.game;

/**
 * The different phases of a {@link RecruitingGame}, ordered by their most logical chronological order.
 *
 */
// FIXME Use in Game? Or delete.
public enum GameState {
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