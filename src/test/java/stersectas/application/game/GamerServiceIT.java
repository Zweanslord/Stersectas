package stersectas.application.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stersectas.BaseIT;
import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;
import stersectas.domain.game.Game;

public class GamerServiceIT extends BaseIT {

	@Autowired
	private GameService gameService;

	@Autowired
	private UserService userService;

	@Test
	@Transactional
	public void currentGamerIsGameMaster() {
		SecurityService securityService = setupSecurityService();
		GamerService gamerService = setupGamerService(securityService);

		Game game = createGame(securityService.currentUser().getUserId().id());

		boolean isMaster = gamerService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertTrue(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerIsNotGameMaster() {
		userService.initializeUser();
		SecurityService securityService = setupSecurityService();
		GamerService gamerService = setupGamerService(securityService);

		Game game = createGame(userService.findByUsername("initial").getUserId().id());

		boolean isMaster = gamerService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertFalse(isMaster);
	}

	private Game createGame(String masterId) {
		CreateGame createGame = new CreateGame();
		createGame.setName("Test-game");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId(masterId);

		gameService.createGame(createGame);
		Game game = gameService.findRecruitingGameByName(createGame.getName());
		return game;
	}

	private SecurityService setupSecurityService() {
		return new SecurityServiceStub(userService);
	}

	private GamerService setupGamerService(SecurityService securityService) {
		return new GamerService(gameService, securityService);
	}

	public class SecurityServiceStub extends SecurityService {

		SecurityServiceStub(UserService userService) {
			super(userService);
			userService.initialiseTestUser();
		}

		@Override
		public String currentUsername() {
			return "test";
		}

	}

}
