package stersectas.application.user;

import java.util.Optional;

import stersectas.domain.user.User;

/**
 * Published language for the User Context.
 */
public interface UserResource {

	User currentUser();

	Optional<User> findByUserId(String userId);

}