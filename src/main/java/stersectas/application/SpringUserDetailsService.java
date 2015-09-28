package stersectas.application;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import stersectas.SecurityConfiguration;
import stersectas.domain.Role;
import stersectas.domain.User;
import stersectas.repositories.UserRepository;

/**
 * Implements UserDetailService for user authorization in {@link SecurityConfiguration}.
 */
@Service("UserDetailsService")
public class SpringUserDetailsService implements UserDetailsService {

	private static final String ROLE_PREFIX = "ROLE_";

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
					convertToGrantedAuthorities(user.getRole()));
		} else {
			throw new UsernameNotFoundException("Username not found.");
		}
	}

	private Set<GrantedAuthority> convertToGrantedAuthorities(Role role) {
		HashSet<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.name()));
		return authorities;
	}

}
