package stersectas.application.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import lombok.val;

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
		val securityService = setupSecurityService();
		val gamerService = setupGamerService(securityService);

		val game = createGame(securityService.currentUser().getUserId().id());

		val isMaster = gamerService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertTrue(isMaster);
	}

	@Test
	@Transactional
	public void currentGamerIsNotGameMaster() {
		userService.initializeUser();
		val securityService = setupSecurityService();
		val gamerService = setupGamerService(securityService);

		val game = createGame(userService.findByUsername("initial").getUserId().id());

		val isMaster = gamerService.isCurrentGamerTheMasterOfGame(game.gameId().id());
		assertFalse(isMaster);
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
