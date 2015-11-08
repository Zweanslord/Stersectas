package stersectas.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import stersectas.application.user.UserService;
import stersectas.domain.user.User;

@Service
public class SecurityService {

	private final UserService userService;

	@Autowired
	public SecurityService(UserService userService) {
		this.userService = userService;
	}

	public User currentUser() {
		return userService.findByUsername(currentUsername());
	}

	protected String currentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}