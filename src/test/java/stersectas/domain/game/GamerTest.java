package stersectas.domain.game;

import static org.junit.Assert.assertEquals;
import lombok.val;

import org.junit.Test;

public class GamerTest {

	@Test
	public void create() {
		val gamer = Gamer.create(new GamerId("testing-id"));
		assertEquals(new GamerId("testing-id"), gamer.gamerId());
		assertEquals(0, gamer.organizedGames().size());
	}

	@Test
	public void createRecruitingGame() {
		val gamer = Gamer.create(new GamerId("testing-id"));

		val game = gamer.createRecruitingGame(
				new GameId("game-test-id"),
				new Name("Fantastische sterrenreizen"),
				new Description("Naar de sterren en daar voorbij!"),
				new MaximumPlayers(4));

		assertEquals(new GamerId("testing-id"), game.masterId());
		assertEquals(new GameId("game-test-id"), game.gameId());
		assertEquals(new Name("Fantastische sterrenreizen"), game.name());
		assertEquals(new Description("Naar de sterren en daar voorbij!"), game.description());
		assertEquals(new MaximumPlayers(4), game.maximumPlayers());
		assertEquals(0, gamer.organizedGames().size());
	}

	@Test
	public void organizeGame() {
		val gamer = Gamer.create(new GamerId("testing-id"));
		val game = gamer.createRecruitingGame(
				new GameId("game-test-id"),
				new Name("Fantastische sterrenreizen"),
				new Description("Naar de sterren en daar voorbij!"),
				new MaximumPlayers(4));

		gamer.organizeGame(game);

		assertEquals(1, gamer.organizedGames().size());
	}

}