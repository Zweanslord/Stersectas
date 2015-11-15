package stersectas.application.game;

import static org.junit.Assert.assertEquals;
import lombok.val;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import stersectas.BaseIT;
import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.Name;
import stersectas.domain.user.UserRepository;

public class GamerServiceIT extends BaseIT {

	@Autowired private UserRepository userRepository;
	@Autowired private GamerRepository gamerRepository;
	@Autowired private UserService userService;

	@Autowired private GamerService gamerService;

	@Test
	@Transactional
	public void findGamerById() {
		userService.initialiseTestUser();
		val testUser = userService.findByUsername("test");

		val gamer = gamerService.findGamerById(testUser.getUserId().id());

		assertEquals(testUser.getUserId().id(), gamer.gamerId().id());
	}

	@Test(expected = GamerNotFoundException.class)
	public void didNotFindGamerById() {
		gamerService.findGamerById("onzinnige-id");
	}

	@Test
	@Transactional
	public void currentGamer() {
		userService.initialiseTestUser();
		val securityService = setupSecurityService();
		val gamerService = setupGamerService(securityService);
		val testUser = userService.findByUsername("test");

		val currentGamer = gamerService.currentGamer();

		assertEquals(testUser.getUserId().id(), currentGamer.gamerId().id());
	}

	@Test
	@Transactional
	public void findNameByGamerId() {
		userService.initialiseTestUser();
		val testUser = userService.findByUsername("test");

		val name = gamerService.findNameByGamerId(new GamerId(testUser.getUserId().id()));

		assertEquals(new Name("test"), name);
	}

	private SecurityService setupSecurityService() {
		return new SecurityServiceStub(userService);
	}

	private GamerService setupGamerService(SecurityService securityService) {
		return new GamerService(userRepository, securityService, gamerRepository);
	}

}
