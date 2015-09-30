package stersectas.view.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.UserService;

@RequestMapping
@Controller
public class ManageUserController {

	private final UserService userService;

	@Autowired
	public ManageUserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/admin/user/{username}")
	public String user(@PathVariable String username, Model model) {
		model.addAttribute("user", userService.findByUsername(username));
		return "admin/user";
	}

	@RequestMapping(value = "/admin/user/{username}/promote", method = RequestMethod.POST)
	public String promote(@PathVariable String username) {
		userService.promoteUserToAdministrator(username);
		return "redirect:/admin/user/" + username;
	}

	@RequestMapping(value = "/admin/user/{username}/demote", method = RequestMethod.POST)
	public String demote(@PathVariable String username) {
		userService.demoteUserToUser(username);
		return "redirect:/admin/user/" + username;
	}

}
