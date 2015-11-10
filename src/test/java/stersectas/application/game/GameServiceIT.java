package stersectas.application.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.application.user.UserService;
import stersectas.domain.game.ArchivedGame;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Description;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;
import stersectas.domain.user.UserId;

public class GameServiceIT extends BaseIT {

	private GameService gameService;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private RecruitingGameRepository recruitingGameRepository;

	@Autowired
	private ArchivedGameRepository archivedGameRepository;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
		gameService = new GameService(
				gameRepository,
				recruitingGameRepository,
				archivedGameRepository);
	}

	@Test
	@Transactional
	public void createGame() {
		CreateGame createGame = new CreateGame();
		createGame.setName("Test-game");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId("0123456789");

		gameService.createGame(createGame);

		RecruitingGame game = gameService.findRecruitingGameByName("Test-game");
		assertEquals("Test-game", game.name().name());
		assertEquals("Description", game.description().description());
		assertEquals(2, game.maximumPlayers().maximumPlayers());
		assertEquals(new UserId("0123456789"), game.masterId());
	}

	@Test(expected = RuntimeException.class)
	@Transactional
	public void createGameWithExistingGame() {
		createRecruitingGame("alreadyTaken");
		CreateGame createGame = new CreateGame();
		createGame.setName("alreadyTaken");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId("0123456789");

		gameService.createGame(createGame);
	}

	@Test
	@Transactional
	public void renameGame() {
		RecruitingGame recruitingGame = createRecruitingGame();
		RenameGame renameGame = new RenameGame(recruitingGame.gameId().id(), "Renamed");

		gameService.renameGame(renameGame);

		RecruitingGame updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new Name("Renamed"), updatedGame.name());
	}

	@Test(expected = RuntimeException.class)
	@Transactional
	public void renameGameWithExistingName() {
		createRecruitingGame("alreadyTaken");
		RecruitingGame recruitingGame = createRecruitingGame();
		RenameGame renameGame = new RenameGame(recruitingGame.gameId().id(), "alreadyTaken");

		gameService.renameGame(renameGame);
	}

	@Test
	@Transactional
	public void changeGamePlayerMaximum() {
		RecruitingGame recruitingGame = createRecruitingGame();
		ChangeGamePlayerMaximum changeGamePlayerMaximum = new ChangeGamePlayerMaximum();
		changeGamePlayerMaximum.setGameId(recruitingGame.gameId().id());
		changeGamePlayerMaximum.setMaximumPlayers(1);

		gameService.changeGamePlayerMaximum(changeGamePlayerMaximum);

		RecruitingGame updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new MaximumPlayers(1), updatedGame.maximumPlayers());
	}

	@Test
	@Transactional
	public void changeGameDescription() {
		RecruitingGame recruitingGame = createRecruitingGame();

		gameService.changeGameDescription(
				new ChangeGameDescription(
						recruitingGame.gameId().id(),
						"Changed Description"));

		RecruitingGame updatedGame = gameService.findRecruitingGameByName(recruitingGame.name().name());
		assertEquals(new Description("Changed Description"), updatedGame.description());
	}

	@Test
	@Transactional
	public void archiveRecruitingGame() {
		RecruitingGame recruitingGame = createRecruitingGame();
		ArchiveGame archiveGame = new ArchiveGame();
		archiveGame.setGameId(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);

		ArchivedGame archivedGame = gameService.findArchivedGameByName(recruitingGame.name().name());
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());
		assertCannotFindGame(recruitingGame.name().name());
	}

	@Test
	@Transactional
	public void archiveArchivedGame() {
		RecruitingGame recruitingGame = createRecruitingGame();
		ArchiveGame archiveGame = new ArchiveGame();
		archiveGame.setGameId(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);
		gameService.archiveGame(archiveGame);

		ArchivedGame archivedGame = gameService.findArchivedGameByName(recruitingGame.name().name());
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());
		assertCannotFindGame(recruitingGame.name().name());
	}

	private RecruitingGame createRecruitingGame() {
		return createRecruitingGame("test-game");
	}

	private RecruitingGame createRecruitingGame(String name) {
		CreateGame createGame = new CreateGame();
		createGame.setName(name);
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId("0123456789");

		gameService.createGame(createGame);

		return gameService.findRecruitingGameByName("test-game");
	}

	private void assertCannotFindGame(String gameName) {
		try {
			gameService.findRecruitingGameByName(gameName);
			fail();
		} catch (Exception e) {
		}
	}

}