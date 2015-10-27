package stersectas.view.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.security.SecurityService;
import stersectas.application.user.UpdateUserPassword;
import stersectas.application.user.UserService;
import stersectas.domain.user.User;

@Controller
@RequestMapping
public class EditPasswordController {

	private final UserService userService;
	private final SecurityService securityService;

	@Autowired
	public EditPasswordController(UserService userService, SecurityService securityService) {
		this.userService = userService;
		this.securityService = securityService;
	}

	@RequestMapping(value = "/profile/password", method = RequestMethod.GET)
	public String editPasswordForm(Model model) {
		User user = securityService.currentUser();

		UpdateUserPassword updateUserPassword = new UpdateUserPassword();
		updateUserPassword.setUsername(user.getUsername());

		model.addAttribute("updateUserPassword", updateUserPassword);

		return "profile/password";
	}

	@RequestMapping(value = "/profile/password", method = RequestMethod.POST)
	public String updatePassword(@Valid UpdateUserPassword updateUserPassword, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			userService.updateUserPassword(updateUserPassword);
			return "redirect:profile/profile";
		}
		return "profile/password";
	}
}
