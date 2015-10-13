package stersectas.external.stub;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.domain.token.VerificationToken;
import stersectas.domain.token.VerificationTokenRepository;
import stersectas.domain.user.UserRepository;
import stersectas.profile.TestingProfile;

@Service
@TestingProfile
public class VerificationTokenLookupService {

	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;

	@Autowired
	public VerificationTokenLookupService(UserRepository userRepository,
			VerificationTokenRepository tokenRepository) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
	}

	@Transactional(readOnly = true)
	public Optional<VerificationToken> findEmailVerificationTokenForUsername(String username) {
		return userRepository.findByUsername(username)
				.flatMap(user -> tokenRepository.findByUser(user));
	}
}