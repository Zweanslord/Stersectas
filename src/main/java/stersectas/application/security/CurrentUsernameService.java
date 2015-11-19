package stersectas.application.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUsernameService {

	public String currentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}