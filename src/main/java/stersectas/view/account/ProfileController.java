package stersectas.view.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.security.SecurityService;
import stersectas.domain.user.User;

@Controller
@RequestMapping
public class ProfileController {

	private final SecurityService securityService;

	@Autowired
	public ProfileController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(Model model) {
		User user = securityService.currentUser();
		model.addAttribute("user", user);
		return "profile/profile";
	}
}
