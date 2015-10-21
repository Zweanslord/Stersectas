package stersectas.domain.game;

import stersectas.domain.user.UserId;

public class GameTestCreator {

	public static RecruitingGame createRecruitingGame() {
		return new RecruitingGame(
				new GameId("0123456789"),
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new UserId("0123456789"));
	}

	public static ArchivedGame createArchivedGame() {
		RecruitingGame game = createRecruitingGame();
		return game.archive();
	}


}
