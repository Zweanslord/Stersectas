package stersectas.application.game;

import static org.junit.Assert.assertEquals;

import java.time.Clock;

import lombok.val;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import stersectas.BaseIT;
import stersectas.application.email.VerificationEmailService;
import stersectas.application.user.UserService;
import stersectas.domain.game.GamerId;
import stersectas.domain.game.GamerRepository;
import stersectas.domain.game.Name;
import stersectas.domain.token.VerificationTokenRepository;
import stersectas.domain.user.UserRepository;

public class GamerServiceIT extends BaseIT {

	// GamerService dependencies
	@Autowired private GamerRepository gamerRepository;

	// UserService dependencies
	@Autowired private UserRepository userRepository;
	@Autowired private VerificationTokenRepository tokenRepository;
	@Autowired private VerificationEmailService verificationEmailService;
	@Autowired private PasswordEncoder encoder;
	@Autowired private Clock clock;

	@Test
	@Transactional
	public void findGamerById() {
		val userService = setupUserService();
		val gamerService = new GamerService(userService, gamerRepository);

		userService.initialiseTestUser();
		val testUser = userService.findByUsername("test");

		val gamer = gamerService.findGamerById(testUser.getUserId().id());

		assertEquals(testUser.getUserId().id(), gamer.gamerId().id());
	}

	@Test(expected = GamerNotFoundException.class)
	public void didNotFindGamerById() {
		val userService = setupUserService();
		val gamerService = new GamerService(userService, gamerRepository);

		gamerService.findGamerById("onzinnige-id");
	}

	@Test
	@Transactional
	public void currentGamer() {
		val userService = setupUserService();
		val gamerService = new GamerService(userService, gamerRepository);

		userService.initialiseTestUser();
		val testUser = userService.findByUsername("test");

		val currentGamer = gamerService.currentGamer();

		assertEquals(testUser.getUserId().id(), currentGamer.gamerId().id());
	}

	private UserService setupUserService() {
		return new UserService(
				userRepository,
				new CurrentUsernameServiceStub(),
				tokenRepository,
				verificationEmailService,
				encoder,
				clock);
	}

	@Test
	@Transactional
	public void findNameByGamerId() {
		val userService = setupUserService();
		val gamerService = new GamerService(userService, gamerRepository);

		userService.initialiseTestUser();
		val testUser = userService.findByUsername("test");

		val name = gamerService.findNameByGamerId(new GamerId(testUser.getUserId().id()));

		assertEquals(new Name("test"), name);
	}


}
