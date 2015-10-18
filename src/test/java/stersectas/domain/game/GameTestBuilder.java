package stersectas.domain.game;

import stersectas.domain.user.UserId;

public class GameTestBuilder {

	public static RecruitingGame createRecruitingTestGame() {
		return new RecruitingGame(
				new GameId("0123456789"),
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new UserId("0123456789"));
	}

	public static ArchivedGame createArchivedTestGame() {
		RecruitingGame game = createRecruitingTestGame();
		return game.archive();
	}


}
