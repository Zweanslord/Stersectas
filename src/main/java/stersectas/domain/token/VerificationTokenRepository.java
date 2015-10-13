package stersectas.domain.token;

import java.util.Optional;

import stersectas.domain.user.User;

public interface VerificationTokenRepository {

	Optional<VerificationToken> findByToken(RandomToken token);

	Optional<VerificationToken> findByUser(User user);

	VerificationToken save(VerificationToken verificationToken);

}