package stersectas.domain.user;

import java.util.Optional;

public interface UserRepository {

	Optional<User> findByUsername(String username);

	Long countByEnabledTrue();

	User save(User user);

	Iterable<User> findAll();

}