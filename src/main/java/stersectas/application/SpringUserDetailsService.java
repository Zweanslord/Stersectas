package stersectas.application;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import stersectas.domain.User;
import stersectas.repositories.UserRepository;

@Service("UserDetailsService")
public class SpringUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public SpringUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.isEnabled(),
					true,
					true,
					true,
					new HashSet<>());
		} else {
			throw new UsernameNotFoundException("Username not found.");
		}
	}

}
