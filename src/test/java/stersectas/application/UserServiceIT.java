package stersectas.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import stersectas.BaseIT;

public class UserServiceIT extends BaseIT {

	@Autowired
	private UserService userService;

	@Test
	@Ignore
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
}
