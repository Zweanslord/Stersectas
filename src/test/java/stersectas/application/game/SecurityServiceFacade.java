package stersectas.application.game;

import stersectas.application.security.SecurityService;
import stersectas.application.user.UserService;

/**
 * Facade for securityService to move around Spring requirements. Current user is the test user.
 */
public class SecurityServiceFacade extends SecurityService {

	SecurityServiceFacade(UserService userService) {
		super(userService);
		userService.initialiseTestUser();
	}

	@Override
	public String currentUsername() {
		return "test";
	}

}