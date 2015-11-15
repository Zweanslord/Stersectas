package stersectas.application.game;

import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;

/**
 * Stub for securityService to move around Spring requirements. Current user is the test user.
 */
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