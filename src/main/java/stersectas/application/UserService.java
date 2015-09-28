package stersectas.application;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import stersectas.SecurityConfiguration;
import stersectas.domain.User;
import stersectas.repositories.UserRepository;
import stersectas.repositories.VerificationTokenRepository;

/**
 * Implements UserDetailService for user authorization in {@link SecurityConfiguration}.
 */
@Service
public class UserService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private static final String INITIAL_USERNAME = "initial";

	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;
	private final EmailService emailService;
	private final PasswordEncoder encoder;
	private final TokenGenerator tokenGenerator;
	private final Clock clock;

	@Autowired
	public UserService(UserRepository userRepository,
			VerificationTokenRepository tokenRepository,
			EmailService emailService,
			PasswordEncoder encoder,
			TokenGenerator tokenGenerator,
			Clock clock) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.encoder = encoder;
		this.tokenGenerator = tokenGenerator;
		this.clock = clock;
	}

	@Transactional
	public void initializeUsers() {
		if (noEnabledUsersExistAndNoInitialUserExists()) {
			createInitialUser();
		}
	}

	private boolean noEnabledUsersExistAndNoInitialUserExists() {
		return userRepository.findByEnabledTrue().isEmpty()
				&& !userRepository.findByUsername(INITIAL_USERNAME).isPresent();
	}

	private void createInitialUser() {
		log.info("Creating initial user.");
		User user = new User(
				INITIAL_USERNAME,
				"test@test.com",
				encoder.encode("password"));
		user.enable();
		userRepository.save(user);
	}

	@Transactional
	public void registerNewUser(RegisterUser userDto) {
		User user = userRepository.save(new User(
				userDto.getUsername(),
				userDto.getEmail(),
				encoder.encode(userDto.getPassword())));

		Token token = tokenGenerator.generateToken();
		tokenRepository.save(new VerificationToken(token, user, LocalDate.now(clock)));

		sendEmailVerification(user, token);
	}

	// TODO: make this method work for all email confirmations (registration or changing email)
	// TODO: make this method agnostic to the domain address
	// TODO: internationalization
	public void sendEmailVerification(User user, Token token) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration Confirmation");
		email.setText("http://localhost:8080/registration-confirmation?token=" + token.toString());
		emailService.send(email);
	}

	@Transactional
	public boolean confirmEmilVerification(String tokenString) {
		Optional<VerificationToken> optionalToken = tokenRepository.findByToken(new Token(tokenString));
		if (!optionalToken.isPresent()) {
			return false;
		}

		VerificationToken token = optionalToken.get();
		if (token.isExpired(LocalDate.now(clock))) {
			return false;
		}

		User user = token.getUser();
		user.enable();

		return true;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException("Username not found.");
		}
	}

}