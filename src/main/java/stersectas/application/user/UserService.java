package stersectas.application.user;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.application.email.VerificationEmailService;
import stersectas.domain.token.RandomToken;
import stersectas.domain.token.VerificationToken;
import stersectas.domain.token.VerificationTokenRepository;
import stersectas.domain.user.User;
import stersectas.domain.user.UserId;
import stersectas.domain.user.UserRepository;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private static final String INITIAL_USERNAME = "initial";
	private static final String TEST_USERNAME = "test";
	private static final String START_PASSWORD = "password";

	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;
	private final VerificationEmailService verificationEmailService;
	private final PasswordEncoder encoder;
	private final Clock clock;

	@Autowired
	public UserService(UserRepository userRepository,
			VerificationTokenRepository tokenRepository,
			VerificationEmailService verificationEmailService,
			PasswordEncoder encoder,
			Clock clock) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.verificationEmailService = verificationEmailService;
		this.encoder = encoder;
		this.clock = clock;
	}

	// FIXME Dependency on HttpServletRequest just seems wrong here
	// but is at the moment required later on to create mail with full url back to application
	// and localised content
	public void registerNewUser(RegisterUser registerUser, HttpServletRequest request) {
		User user = registerUser(registerUser);
		sendEmailVerification(user, request);
	}

	@Transactional
	private User registerUser(RegisterUser registerUser) {
		return userRepository.save(new User(
				userRepository.nextIdentity(),
				registerUser.getUsername(),
				registerUser.getEmail(),
				encoder.encode(registerUser.getPassword())));
	}

	@Transactional
	private void sendEmailVerification(User user, HttpServletRequest request) {
		VerificationToken verificationToken = VerificationToken.create(user, LocalDateTime.now(clock));
		verificationToken = tokenRepository.save(verificationToken);

		verificationEmailService.sendEmailVerification(user, verificationToken, request);
	}

	@Transactional
	public boolean confirmEmailVerification(String tokenString) {
		Optional<VerificationToken> optionalToken = tokenRepository.findByToken(RandomToken.from(tokenString));
		if (!optionalToken.isPresent()) {
			return false;
		}

		VerificationToken token = optionalToken.get();
		if (token.isExpired(LocalDateTime.now(clock))) {
			return false;
		}

		User user = token.getUser();
		user.enable();

		return true;
	}

	@Transactional
	public void promoteUserToAdministrator(String username) {
		User user = userRepository.findByUsername(username).get();
		user.promoteToAdministrator();
	}

	@Transactional
	public void demoteUserToUser(String username) {
		User user = userRepository.findByUsername(username).get();
		user.demoteToUser();
	}

	@Transactional
	public void updateUserPassword(UserId userId, String plainPassword) {
		User user = userRepository.findByUserId(userId).get();
		String encodedPassword = encoder.encode(plainPassword);
		user.setPassword(encodedPassword);
	}

	@Transactional(readOnly = true)
	public Iterable<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	@Transactional
	public void initializeUser() {
		if (noEnabledUsersExist()) {
			ensureInitialUserIsPresentAndEnabled();
		}
	}

	private boolean noEnabledUsersExist() {
		return userRepository.countByEnabledTrue() == 0;
	}

	private void ensureInitialUserIsPresentAndEnabled() {
		Optional<User> optionalUser = userRepository.findByUsername(INITIAL_USERNAME);
		if (optionalUser.isPresent()) {
			ensureUserIsEnabledAndAdministrator(optionalUser.get());
		} else {
			createInitialUser();
		}
	}

	private void ensureUserIsEnabledAndAdministrator(User user) {
		if (user.isDisabled()) {
			user.enable();
		}
		user.promoteToAdministrator();
	}

	private void createInitialUser() {
		log.info("Creating initial user.");
		User user = new User(
				userRepository.nextIdentity(),
				INITIAL_USERNAME,
				"test@test.com",
				encoder.encode(START_PASSWORD));
		user.enable();
		user.promoteToAdministrator();
		userRepository.save(user);
	}

	@Transactional
	public void initialiseTestUser() {
		log.info("Creating test user.");
		Optional<User> optionalUser = userRepository.findByUsername(TEST_USERNAME);
		if (!optionalUser.isPresent()) {
			User user = new User(
					userRepository.nextIdentity(),
					TEST_USERNAME,
					"test@test.com",
					encoder.encode(START_PASSWORD));
			user.enable();
			userRepository.save(user);
		}
	}

}