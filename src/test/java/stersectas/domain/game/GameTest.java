package stersectas.domain.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {

	@Test
	public void createGame() {
		Game game = new Game(
				new Name("Test-Game"),
				new Description("A game made for testing purposes"),
				new MaximumPlayers(4),
				new Master("owner"));

		assertEquals(GameState.PREPARING, game.state());
		assertEquals(new Name("Test-Game"), game.name());
		assertEquals(new Description("A game made for testing purposes"), game.description());
		assertEquals(new MaximumPlayers(4), game.maximumPlayers());
	}

	@Test
	public void adjustMaximumOfPlayers() {
		Game game = GameTestBuilder.createPreparingTestGame();

		game.adjustMaximumOfPlayers(new MaximumPlayers(2));

		assertEquals(2, game.maximumPlayers().maximum());
	}

	@Test(expected = IllegalStateException.class)
	public void adjustMaximumOfPlayersAfterRecruitment() {
		Game game = GameTestBuilder.createRunningTestGame();

		game.adjustMaximumOfPlayers(new MaximumPlayers(2));
	}

	@Test
	public void changeName() {
		Game game = GameTestBuilder.createPreparingTestGame();

		game.changeName(new Name("Another Name"));

		assertEquals(new Name("Another Name"), game.name());
	}

	@Test(expected = IllegalStateException.class)
	public void changeNameAfterFinish() {
		Game game = GameTestBuilder.createFinishedTestGame();

		game.changeName(new Name("Another Name"));
	}

	@Test
	public void changeDescription() {
		Game game = GameTestBuilder.createPreparingTestGame();

		game.changeDescription(new Description("Another Description"));

		assertEquals(new Description("Another Description"), game.description());
	}

	@Test(expected = IllegalStateException.class)
	public void changeDescriptionAfterFinish() {
		Game game = GameTestBuilder.createFinishedTestGame();

		game.changeDescription(new Description("Another Description"));
	}

	@Test
	public void openRecruitmentDuringPreparation() {
		Game game = GameTestBuilder.createPreparingTestGame();

		game.openRecruitment();

		assertEquals(game.state(), GameState.RECRUITING);
	}

	@Test(expected = IllegalStateException.class)
	public void openRecruitmentAfterFinish() {
		Game game = GameTestBuilder.createFinishedTestGame();

		game.openRecruitment();
	}

	// TODO start game

	@Test
	public void archivePreparingGame() {
		Game game = GameTestBuilder.createPreparingTestGame();

		game.archive();

		assertEquals(game.state(), GameState.ARCHIVED);
	}

	@Test
	public void archiveRecruitingGame() {
		Game game = GameTestBuilder.createRecruitingTestGame();

		game.archive();

		assertEquals(game.state(), GameState.ARCHIVED);
	}

	@Test
	public void archiveRunningGame() {
		Game game = GameTestBuilder.createRunningTestGame();

		game.archive();

		assertEquals(game.state(), GameState.ARCHIVED);
	}

	@Test
	public void archiveFinishedGame() {
		Game game = GameTestBuilder.createFinishedTestGame();

		game.archive();

		assertEquals(game.state(), GameState.ARCHIVED);
	}

	@Test
	public void archiveArchivedGame() {
		Game game = GameTestBuilder.createArchivedTestGame();

		game.archive();

		assertEquals(game.state(), GameState.ARCHIVED);
	}

}