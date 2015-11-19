package stersectas.application.game;

import stersectas.application.security.CurrentUsernameService;

/**
 * Stub for currentUsernameService to move around Spring requirements. Current user is the test user, or can be set.
 */
public class CurrentUsernameServiceStub extends CurrentUsernameService {

	private String currentUsername = "test";

	CurrentUsernameServiceStub() {
		super();
	}

	CurrentUsernameServiceStub(String currentUsername) {
		this.currentUsername = currentUsername;
	}

	@Override
	public String currentUsername() {
		return currentUsername;
	}

	public void currentUsername(String currentUsername) {
		this.currentUsername = currentUsername;
	}

}