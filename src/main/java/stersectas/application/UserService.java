package stersectas.application;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.domain.User;
import stersectas.repositories.UserRepository;
import stersectas.repositories.VerificationTokenRepository;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private static final String INITIAL_USERNAME = "initial";
	private static final String TEST_USERNAME = "test";

	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;
	private final EmailService emailService;
	private final PasswordEncoder encoder;
	private final Clock clock;

	@Autowired
	public UserService(UserRepository userRepository,
			VerificationTokenRepository tokenRepository,
			EmailService emailService,
			PasswordEncoder encoder,
			Clock clock) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.encoder = encoder;
		this.clock = clock;
	}

	public void registerNewUser(RegisterUser registerUser) {
		User user = registerUser(registerUser);
		sendEmailVerification(user);
	}

	@Transactional
	private User registerUser(RegisterUser registerUser) {
		return userRepository.save(new User(
				registerUser.getUsername(),
				registerUser.getEmail(),
				encoder.encode(registerUser.getPassword())));
	}

	@Transactional
	private void sendEmailVerification(User user) {
		VerificationToken verificationToken = VerificationToken.create(user, LocalDateTime.now(clock));

		tokenRepository.save(verificationToken);

		sendEmailVerification(user, verificationToken);
	}

	// TODO: make this method work for all email confirmations (registration or changing email)
	// TODO: make this method agnostic to the domain address
	// TODO: internationalization
	public void sendEmailVerification(User user, VerificationToken verificationToken) {
		log.info("Sending verification token %s to %s for registration of %s",
				verificationToken.tokenString(), user.getEmail(), user.getUsername());
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration Confirmation");
		email.setText("http://localhost:8080/registration-confirmation?token=" + verificationToken.tokenString());
		emailService.send(email);
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
				INITIAL_USERNAME,
				"test@test.com",
				encoder.encode("password"));
		user.enable();
		user.promoteToAdministrator();
		userRepository.save(user);
	}

	@Transactional
	public void initialiseTestUser() {
		Optional<User> optionalUser = userRepository.findByUsername(TEST_USERNAME);
		if (!optionalUser.isPresent()) {
			User user = new User(
					TEST_USERNAME,
					"test@test.com",
					encoder.encode("password"));
			user.enable();
			userRepository.save(user);
		}
	}

}