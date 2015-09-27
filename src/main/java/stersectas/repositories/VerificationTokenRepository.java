package stersectas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import stersectas.application.VerificationToken;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
	Optional<VerificationToken> findByToken(String token);
}
