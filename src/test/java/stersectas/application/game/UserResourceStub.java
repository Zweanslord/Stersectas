package stersectas.application.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import stersectas.application.user.UserResource;
import stersectas.domain.user.User;
import stersectas.domain.user.UserId;

public class UserResourceStub implements UserResource {

	public static final String DEFAULT_ID = "USER-TEST-IDENTIFIER";

	private static String CURRENT_ID = DEFAULT_ID;

	private Map<String, User> users;

	public UserResourceStub() {
		users = new HashMap<>();
		reset();
	}

	@Override
	public User currentUser() {
		return findByUserId(CURRENT_ID).get();
	}

	@Override
	public Optional<User> findByUserId(String userId) {
		return Optional.ofNullable(users.get(userId));
	}

	public void addUser(String userId) {
		users.put(userId, new User(
				new UserId(userId),
				"aUser",
				"user@user.com",
				"anotherPassword"
				));
	}

	public void currentUser(String userId) {
		if (users.containsKey(userId)) {
			CURRENT_ID = userId;
		}
	}

	public void reset() {
		users.clear();
		users.put(DEFAULT_ID, new User(
				new UserId(DEFAULT_ID),
				"TestUser",
				"test@test.com",
				"aPassword"));
	}

}
