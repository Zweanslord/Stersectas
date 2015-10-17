package stersectas.domain.game;

import stersectas.domain.user.UserId;

public class GameTestBuilder {

	public static Game createPreparingTestGame() {
		return new Game(new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new Master(
						new UserId("0123456789"),
						new Name("owner")));
	}

	public static Game createRecruitingTestGame() {
		Game game = createPreparingTestGame();
		game.openRecruitment();
		return game;
	}

	public static Game createRunningTestGame() {
		Game game = createRecruitingTestGame();
		game.start();
		return game;
	}

	public static Game createFinishedTestGame() {
		Game game = createRunningTestGame();
		game.finish();
		return game;
	}

	public static Game createArchivedTestGame() {
		Game game = createFinishedTestGame();
		game.archive();
		return game;
	}


}
