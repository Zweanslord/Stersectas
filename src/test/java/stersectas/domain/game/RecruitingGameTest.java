package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import stersectas.domain.user.UserId;

public class RecruitingGameTest {

	@Test
	public void createGame() {
		RecruitingGame game = new RecruitingGame(
				new GameId("0123456789"),
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new GamerId("1234567890"));

		assertEquals(new GameId("0123456789"), game.gameId());
		assertEquals(new Name("Test-Game"), game.name());
		assertEquals(new Description("A game made for testing purposes"), game.description());
		assertEquals(new MaximumPlayers(4), game.maximumPlayers());
		assertEquals(new UserId("1234567890"), game.masterId());
	}

	@Test
	public void adjustMaximumOfPlayers() {
		RecruitingGame game = GameTestCreator.createRecruitingGame();

		game.adjustMaximumOfPlayers(new MaximumPlayers(2));

		assertEquals(2, game.maximumPlayers().maximumPlayers());
	}

	@Test
	public void rename() {
		RecruitingGame game = GameTestCreator.createRecruitingGame();

		game.rename(new Name("Another Name"));

		assertEquals(new Name("Another Name"), game.name());
	}

	@Test
	public void changeDescription() {
		RecruitingGame game = GameTestCreator.createRecruitingGame();

		game.changeDescription(new Description("Another Description"));

		assertEquals(new Description("Another Description"), game.description());
	}

	@Test
	@Ignore
	public void addPlayer() {
		// TODO GAME add player
	}

	@Test
	@Ignore
	public void start() {
		// TODO GAME start
	}

	@Test
	public void archiveRecruitingGame() {
		RecruitingGame recruitingGame = new RecruitingGame(
				new GameId("0123456789"),
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new GamerId("1234567890"));

		ArchivedGame archivedGame = recruitingGame.archive();

		assertEquals(new Name("Test-Game"), archivedGame.name());
		assertEquals(new Description("A game made for testing purposes"), archivedGame.description());
		assertEquals(new MaximumPlayers(4), archivedGame.maximumPlayers());
		assertEquals(new UserId("1234567890"), archivedGame.masterId());
	}

}