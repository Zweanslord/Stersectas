package stersectas.application.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stersectas.BaseIT;
import stersectas.application.email.Email;
import stersectas.domain.user.User;
import stersectas.domain.user.UserRepository;
import stersectas.external.stub.EmailServiceStub;
import stersectas.external.stub.TimeTravellingClock;
import stersectas.external.stub.VerificationTokenLookupService;

public class UserServiceIT extends BaseIT {

	private static final MockHttpServletRequest REQUEST = new MockHttpServletRequest();

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TimeTravellingClock clock;

	@Autowired
	private EmailServiceStub emailServiceStub;

	@Autowired
	private VerificationTokenLookupService verificationTokenLookupService;

	@Test
	@Transactional
	public void registerUser() {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername("test-user");
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("12345");

		userService.registerNewUser(registerUser, REQUEST);
		Email email = emailServiceStub.getLastEmail();

		User testUser = userService.findByUsername("test-user");
		assertEquals("test-user", testUser.getUsername());
		assertNotEquals("12345", testUser.getPassword());
		assertTrue(new BCryptPasswordEncoder().matches("12345", testUser.getPassword()));
		assertFalse(testUser.isEnabled());
		assertNotNull(email);
		assertEquals("test@test.com", email.getTo());
	}

	@Test
	@Transactional
	public void confirmRegistrationImmediately() {
		userService.registerNewUser(createRegisterUser("test-user"), REQUEST);
		String token = findToken("test-user");

		boolean confirmation = userService.confirmEmailVerification(token);
		User user = userService.findByUsername("test-user");

		assertTrue(confirmation);
		assertTrue(user.isEnabled());
	}

	private RegisterUser createRegisterUser(String username) {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername(username);
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("12345");
		return registerUser;
	}

	private String findToken(String userName) {
		return verificationTokenLookupService.findEmailVerificationTokenForUsername("test-user").get().tokenString();
	}

	@Test
	@Transactional
	public void confirmRegistrationAnHourLater() {
		LocalDateTime dateTime = LocalDateTime.parse("2015-09-28T14:42:21");
		clock.travelThroughTimeTo(dateTime);
		userService.registerNewUser(createRegisterUser("test-user"), REQUEST);

		clock.travelThroughTimeTo(dateTime.plusHours(1));
		boolean confirmation = userService.confirmEmailVerification(findToken("test-user"));
		User user = userService.findByUsername("test-user");

		assertTrue(confirmation);
		assertTrue(user.isEnabled());
	}

	@Test
	@Transactional
	public void registrationExpired() {
		LocalDateTime dateTime = LocalDateTime.parse("2015-09-28T14:42:21");
		clock.travelThroughTimeTo(dateTime);
		userService.registerNewUser(createRegisterUser("test-user"), REQUEST);

		clock.travelThroughTimeTo(dateTime.plusDays(1).plusSeconds(1));
		boolean confirmation = userService.confirmEmailVerification(findToken("test-user"));
		User user = userService.findByUsername("test-user");

		assertFalse(confirmation);
		assertFalse(user.isEnabled());
	}

	@Test
	@Transactional
	public void createInitialUser() {
		userService.initializeUser();
		User initialUser = userService.findByUsername("initial");
		assertEquals("initial", initialUser.getUsername());
		assertTrue(initialUser.isEnabled());
	}

	@Test
	@Transactional
	public void alreadyInitialisedUsers() {
		registerAndEnableAUser();
		assertFalse(userRepository.findByUsername("initial").isPresent());

		userService.initializeUser();

		assertFalse(userRepository.findByUsername("initial").isPresent());
	}

	private void registerAndEnableAUser() {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername("test-user");
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("12345");
		userService.registerNewUser(registerUser, REQUEST);
		User user = userService.findByUsername("test-user");
		user.enable();
		userRepository.save(user);
	}

	@Test
	@Transactional
	public void initialiseWithDisabledInitialUser() {
		registerInitialUser();
		assertFalse(userService.findByUsername("initial").isEnabled());

		userService.initializeUser();

		User initialUser = userService.findByUsername("initial");
		assertEquals("initial", initialUser.getUsername());
		assertTrue(initialUser.isEnabled());
	}

	private void registerInitialUser() {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername("initial");
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("password");
		userService.registerNewUser(registerUser, REQUEST);
	}

	@Test
	@Transactional
	public void promoteUser() {
		registerAndEnableAUser();
		userService.promoteUserToAdministrator("test-user");

		User user = userService.findByUsername("test-user");
		assertTrue(user.isAdministrator());
	}

}
