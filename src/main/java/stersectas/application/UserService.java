package stersectas.application;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
	private final PasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository userRepository,
			VerificationTokenRepository tokenRepository,
			JavaMailSender mailSender,
			PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.mailSender = mailSender;
		this.encoder = encoder;
	}
	
	// TODO: remove once we no longer need a test user
	public void createTestUser() {
		if (!userRepository.findByUsername("test").isPresent()) {
			User user = new User(
					"test",
					"test@test.com",
					encoder.encode("password"));
			user.setEnabled(true);
			userRepository.save(user);
		}
	}
	
	@Transactional
	public void registerNewUser(RegisterUser userDto) {
		User user = userRepository.save(new User(
				userDto.getUsername(),
				userDto.getEmail(),
				encoder.encode(userDto.getPassword())));
		
		String token = UUID.randomUUID().toString();
		tokenRepository.save(new VerificationToken(token, user));
		
		sendEmailVerification(user, token);
	}
	
	// TODO: make this method work for all email confirmations (registration or changing email)
	// TODO: make this method agnostic to the domain address
	// TODO: internationalization
	// TODO: make sending mail async to prevent long wait times on user side
	public void sendEmailVerification(User user, String token) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Registration Confirmation");
		email.setText("http://localhost:8080/registration-confirmation.html?token=" + token);
		mailSender.send(email);
	}
	
	@Transactional
	public boolean confirmEmilVerification(String tokenString) {
		Optional<VerificationToken> optionalToken = tokenRepository.findByToken(tokenString);
		if (!optionalToken.isPresent()) return false;
		VerificationToken token = optionalToken.get();
		
		Calendar cal = Calendar.getInstance();
		if (token.getExpirationDate().getTime() <= cal.getTime().getTime()) return false;
		
		User user = token.getUser();
		user.setEnabled(true);
		
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