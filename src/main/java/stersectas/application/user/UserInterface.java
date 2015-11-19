package stersectas.application.user;

import java.util.Optional;

import stersectas.domain.user.User;

public interface UserInterface {

	User currentUser();

	Optional<User> findByUserId(String userId);

}