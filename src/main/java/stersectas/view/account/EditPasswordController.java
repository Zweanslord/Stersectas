package stersectas.view.account;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.user.UpdateUserPassword;
import stersectas.application.user.UserService;
import stersectas.domain.user.User;

@Controller
@RequestMapping
public class EditPasswordController {

	private final UserService userService;

	@Autowired
	public EditPasswordController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/profile/password", method = RequestMethod.GET)
	public String editPasswordForm(Model model) {
		model.addAttribute("updateUserPassword", new UpdateUserPassword());
		return "profile/password";
	}

	@RequestMapping(value = "/profile/password", method = RequestMethod.POST)
	public String updatePassword(@Valid UpdateUserPassword updateUserPassword, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/profile/password";
		}
		User user = userService.currentUser();
		userService.updateUserPassword(user.getUserId(), updateUserPassword.getPassword());
		return "redirect:/profile";
	}
}
