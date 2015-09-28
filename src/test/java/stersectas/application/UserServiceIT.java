package stersectas.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stersectas.BaseIT;
import stersectas.domain.User;
import stersectas.repositories.UserRepository;
import stersectas.stub.TimeTravellingClock;

public class UserServiceIT extends BaseIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TimeTravellingClock clock;

	@Test
	@Transactional
	public void registerUser() {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername("test-user");
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("12345");

		userService.registerNewUser(registerUser);

		UserDetails testUser = userService.loadUserByUsername("test-user");
		assertEquals("test-user", testUser.getUsername());
		assertNotEquals("12345", testUser.getPassword());
		assertTrue(new BCryptPasswordEncoder().matches("12345", testUser.getPassword()));
		assertFalse(testUser.isEnabled());
	}

	@Test
	@Transactional
	public void confirmRegistrationImmediately() {
		userService.registerNewUser(createRegisterUser("test-user"));

		boolean confirmation = userService.confirmEmailVerification("test-token");
		User user = userRepository.findByUsername("test-user").get();

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

	@Test
	@Transactional
	public void confirmRegistrationAnHourLater() {
		LocalDateTime dateTime = LocalDateTime.parse("2015-09-28T14:42:21");
		clock.travelThroughTimeTo(dateTime);
		userService.registerNewUser(createRegisterUser("test-user"));

		clock.travelThroughTimeTo(dateTime.plusHours(1));
		boolean confirmation = userService.confirmEmailVerification("test-token");
		User user = userRepository.findByUsername("test-user").get();

		assertTrue(confirmation);
		assertTrue(user.isEnabled());
	}

	@Test
	@Transactional
	public void registrationExpired() {
		LocalDateTime dateTime = LocalDateTime.parse("2015-09-28T14:42:21");
		clock.travelThroughTimeTo(dateTime);
		userService.registerNewUser(createRegisterUser("test-user"));

		clock.travelThroughTimeTo(dateTime.plusDays(1).plusSeconds(1));
		boolean confirmation = userService.confirmEmailVerification("test-token");
		User user = userRepository.findByUsername("test-user").get();

		assertFalse(confirmation);
		assertFalse(user.isEnabled());
	}

	@Test
	@Transactional
	public void createInitialUser() {
		userService.initializeUsers();
		UserDetails initialUser = userService.loadUserByUsername("initial");
		assertEquals("initial", initialUser.getUsername());
	}

	@Test
	@Transactional
	public void alreadyInitialisedUsers() {
		registerAndEnableAUser();
		assertFalse(userRepository.findByUsername("initial").isPresent());

		userService.initializeUsers();

		assertFalse(userRepository.findByUsername("initial").isPresent());
	}

	private void registerAndEnableAUser() {
		RegisterUser registerUser = new RegisterUser();
		registerUser.setUsername("test-user");
		registerUser.setEmail("test@test.com");
		registerUser.setPassword("12345");
		userService.registerNewUser(registerUser);
		User user = userRepository.findByUsername("test-user").get();
		user.enable();
		userRepository.save(user);
	}

}
