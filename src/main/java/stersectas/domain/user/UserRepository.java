package stersectas.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

	default UserId nextIdentity() {
		return new UserId(UUID.randomUUID().toString().toUpperCase());
	}

	Optional<User> findByUserId(UserId userId);

	Optional<User> findByUsername(String username);

	Long countByEnabledTrue();

	User save(User user);

	Iterable<User> findAll();

}