package stersectas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import stersectas.application.RandomToken;
import stersectas.application.VerificationToken;
import stersectas.domain.User;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(RandomToken token);

	Optional<VerificationToken> findByUser(User user);

}
