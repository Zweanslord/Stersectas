package stersectas.application.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import lombok.val;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.domain.game.Description;
import stersectas.domain.game.Gamer;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;

public class GameServiceIT extends BaseIT {

	@Autowired private GamerRepository gamerRepository;
	@Autowired private GameService gameService;

	@Test
	@Transactional
	public void createGame() {
		val gamerId = "test-gamer-id";
		gamerRepository.save(Gamer.create(new GamerId(gamerId)));

		gameService.createGame(new CreateGame(
				"Test-game",
				"Description",
				2,
				gamerId));

		val game = gameService.findRecruitingGameByName("Test-game");
		assertEquals("Test-game", game.name().name());
		assertEquals("Description", game.description().description());
		assertEquals(2, game.maximumPlayers().maximumPlayers());
		assertEquals(new GamerId(gamerId), game.masterId());
	}

	@Test(expected = RuntimeException.class)
	@Transactional
	public void createGameWithExistingGame() {
		createRecruitingGame("alreadyTaken");
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name("alreadyTaken")
						.build());
	}

	@Test
	@Transactional
	public void renameGame() {
		val recruitingGame = createRecruitingGame();
		val renameGame = new RenameGame(recruitingGame.gameId().id(), "Renamed");

		gameService.renameGame(renameGame);

		val updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new Name("Renamed"), updatedGame.name());
	}

	@Test(expected = RuntimeException.class)
	@Transactional
	public void renameGameWithExistingName() {
		createRecruitingGame("alreadyTaken");
		val recruitingGame = createRecruitingGame();
		val renameGame = new RenameGame(recruitingGame.gameId().id(), "alreadyTaken");

		gameService.renameGame(renameGame);
	}

	@Test
	@Transactional
	public void changeGamePlayerMaximum() {
		val recruitingGame = createRecruitingGame();
		val changeGamePlayerMaximum = new ChangeGameMaximumPlayers(recruitingGame.gameId().id(), 1);

		gameService.changeGameMaximumPlayers(changeGamePlayerMaximum);

		val updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new MaximumPlayers(1), updatedGame.maximumPlayers());
	}

	@Test
	@Transactional
	public void changeGameDescription() {
		val recruitingGame = createRecruitingGame();

		gameService.changeGameDescription(
				new ChangeGameDescription(
						recruitingGame.gameId().id(),
						"Changed Description"));

		val updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new Description("Changed Description"), updatedGame.description());
	}

	@Test
	@Transactional
	public void archiveRecruitingGame() {
		val recruitingGame = createRecruitingGame();
		val archiveGame = new ArchiveGame(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);

		val archivedGame = gameService.findArchivedGameByName(recruitingGame.name().name());
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());
		assertCannotFindGame(recruitingGame.name().name());
	}

	@Test
	@Transactional
	public void archiveArchivedGame() {
		val recruitingGame = createRecruitingGame();
		val archiveGame = new ArchiveGame(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);
		gameService.archiveGame(archiveGame);

		val archivedGame = gameService.findArchivedGameByName(recruitingGame.name().name());
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());
		assertCannotFindGame(recruitingGame.name().name());
	}

	private RecruitingGame createRecruitingGame() {
		return createRecruitingGame("test-game");
	}

	private RecruitingGame createRecruitingGame(String name) {
		val gamerId = "test-gamer-id";
		gamerRepository.save(Gamer.create(new GamerId(gamerId)));
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.masterId(gamerId)
						.build());

		return gameService.findRecruitingGameByName(name);
	}

	private void assertCannotFindGame(String gameName) {
		try {
			gameService.findRecruitingGameByName(gameName);
			fail();
		} catch (Exception e) {
		}
	}

}