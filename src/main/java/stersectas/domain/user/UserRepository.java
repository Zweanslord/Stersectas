package stersectas.domain.user;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

	Optional<User> findByUserId(UserId userId);

	Optional<User> findByUsername(String username);

	Long countByEnabledTrue();

	User save(User user);

	Iterable<User> findAll();

	default UserId nextIdentity() {
		return new UserId(UUID.randomUUID().toString().toUpperCase());
	}

}