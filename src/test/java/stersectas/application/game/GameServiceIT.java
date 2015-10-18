package stersectas.application.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;
import stersectas.domain.game.ArchivedGame;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.RecruitingGame;
import stersectas.domain.game.RecruitingGameRepository;
import stersectas.domain.user.User;

public class GameServiceIT extends BaseIT {

	private GameService gameService;

	private SecurityService securityService;

	@Autowired
	private RecruitingGameRepository recruitingGameRepository;

	@Autowired
	private ArchivedGameRepository archivedGameRepository;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {
		userService.initialiseTestUser();
		User testUser = userService.findByUsername("test");

		securityService = Mockito.mock(SecurityService.class);
		Mockito.when(securityService.currentUser()).thenReturn(testUser);

		gameService = new GameService(
				securityService,
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

		gameService.createGame(createGame);

		RecruitingGame game = gameService.findRecruitingGameByName("Test-game");
		assertEquals("Test-game", game.name().name());
		assertEquals("Description", game.description().description());
		assertEquals(2, game.maximumPlayers().maximum());
		assertEquals(userService.findByUsername("test").getUserId(), game.masterId());
	}

	@Test
	@Transactional
	public void archiveGame() {
		RecruitingGame recruitingGame = createRecruitingGame("test-game");

		ArchiveGame archiveGame = new ArchiveGame();
		archiveGame.setGameId(recruitingGame.gameId().id());

		gameService.archiveGame(archiveGame);

		ArchivedGame archivedGame = gameService.findArchivedGameByName("test-game");
		assertEquals(recruitingGame.gameId().id(), archivedGame.gameId().id());

		assertNull(gameService.findRecruitingGameByName("test-game"));
	}

	private RecruitingGame createRecruitingGame(String name) {
		CreateGame createGame = new CreateGame();
		createGame.setName(name);
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);

		gameService.createGame(createGame);

		return gameService.findRecruitingGameByName(name);
	}

}