package stersectas.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stersectas.BaseIT;
import stersectas.domain.User;
import stersectas.repositories.UserRepository;

public class UserServiceIT extends BaseIT {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

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
