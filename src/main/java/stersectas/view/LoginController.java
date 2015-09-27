package stersectas.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.application.UserService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		userService.createTestUser(); // TODO: delete test user creation
		return "login";
	}

}