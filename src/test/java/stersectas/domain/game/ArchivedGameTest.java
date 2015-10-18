package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import stersectas.domain.user.UserId;

public class ArchivedGameTest {

	@Test
	public void newArchivedGame() {
		ArchivedGame game = new ArchivedGame(
				new GameId("0123456789"),
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new UserId("1234567890"));

		assertEquals(new GameId("0123456789"), game.gameId());
		assertEquals(new Name("Test-Game"), game.name());
		assertEquals(new Description("A game made for testing purposes"), game.description());
		assertEquals(new MaximumPlayers(4), game.maximumPlayers());
		assertEquals(new UserId("1234567890"), game.masterId());
	}

}