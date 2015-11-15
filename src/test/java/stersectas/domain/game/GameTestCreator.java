package stersectas.domain.game;

import lombok.val;

public class GameTestCreator {

	public static RecruitingGame createRecruitingGame() {
		return Gamer.create(new GamerId("0123456789"))
				.createRecruitingGame(
						new GameId("0123456789"),
						new Name("Test-Game"),
						new Description("A game made for testing purposes"),
						new MaximumPlayers(4));
	}

	public static ArchivedGame createArchivedGame() {
		val game = createRecruitingGame();
		return game.archive();
	}


}
