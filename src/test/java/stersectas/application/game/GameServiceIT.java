package stersectas.application.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.transaction.Transactional;

import lombok.val;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.Description;
import stersectas.domain.game.Game;
import stersectas.domain.game.GameRepository;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.MaximumPlayers;
import stersectas.domain.game.Name;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;
import stersectas.domain.user.UserRepository;

public class GameServiceIT extends BaseIT {

	@Autowired private GamerService gamerService;
	@Autowired private GameRepository gameRepository;
	@Autowired private RecruitingGameRepository recruitingGameRepository;
	@Autowired private ArchivedGameRepository archivedGameRepository;
	@Autowired private UserService userService;
	@Autowired private UserRepository userRepository;
	@Autowired private GamerRepository gamerRepository;

	private GameService gameService;

	@Before
	public void setUp() {
		gameService = new GameService(
				gameRepository,
				recruitingGameRepository,
				archivedGameRepository,
				gamerService);
	}

	@Test
	@Transactional
	public void createGame() {
		userService.initialiseTestUser();
		val testUserId = findTestUserId();

		gameService.createGame(new CreateGame(
				"Test-game",
				"Description",
				2,
				testUserId));

		val game = gameService.findRecruitingGameByName("Test-game");
		assertEquals("Test-game", game.name().name());
		assertEquals("Description", game.description().description());
		assertEquals(2, game.maximumPlayers().maximumPlayers());
		assertEquals(new GamerId(testUserId), game.masterId());
	}

	private String findTestUserId() {
		return userService.findByUsername("test").getUserId().id();
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
		val archiveGame = new ArchiveGame();
		archiveGame.setGameId(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);

		val archivedGame = gameService.findArchivedGameByName(recruitingGame.name().name());
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());
		assertCannotFindGame(recruitingGame.name().name());
	}

	@Test
	@Transactional
	public void archiveArchivedGame() {
		val recruitingGame = createRecruitingGame();
		val archiveGame = new ArchiveGame();
		archiveGame.setGameId(recruitingGame.gameId().id());

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
		userService.initialiseTestUser();
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.masterId(findTestUserId())
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

	@Test
	@Transactional
	public void currentGamerIsGameMaster() {
		val securityService = setupSecurityService();
		val gamerService = setupGamerService(securityService);
		val gameService = setupGameService(gamerService);

		val game = createGame(securityService.currentUser().getUserId().id());

		val isMaster = gameService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertTrue(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerIsNotGameMaster() {
		userService.initializeUser();
		val securityService = setupSecurityService();
		val gamerService = setupGamerService(securityService);
		val gameService = setupGameService(gamerService);

		val game = createGame(userService.findByUsername("initial").getUserId().id());

		val isMaster = gameService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertFalse(isMaster);
	}

	private SecurityService setupSecurityService() {
		return new SecurityServiceStub(userService);
	}

	private GamerService setupGamerService(SecurityService securityService) {
		return new GamerService(userRepository, securityService, gamerRepository);
	}

	private GameService setupGameService(GamerService gamerService) {
		return new GameService(gameRepository, recruitingGameRepository, archivedGameRepository, gamerService);
	}

	private Game createGame(String masterId) {
		val name = "Test-game";
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.masterId(masterId)
						.build());
		return gameService.findRecruitingGameByName(name);
	}

}