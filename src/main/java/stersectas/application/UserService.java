package stersectas.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import stersectas.SecurityConfiguration;
import stersectas.domain.User;
import stersectas.repositories.UserRepository;

/**
 * Implements UserDetailService for user authorization in {@link SecurityConfiguration}.
 */
@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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