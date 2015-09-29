package stersectas.view.admin;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.application.UserService;
import stersectas.domain.User;

@RequestMapping
@Controller
public class UsersController {

	private static Comparator<User> comparator = (user, otherUser) -> {
		final int EQUAL = 0;
		int comparison = user.getRole().compareTo(otherUser.getRole());
		if (comparison == EQUAL) {
			comparison = Boolean.compare(user.isEnabled(), otherUser.isEnabled());
		}
		if (comparison == EQUAL) {
			comparison = user.getUsername().compareTo(otherUser.getUsername());
		}
		return comparison;
	};

	private UserService userService;

	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/admin/users")
	public String users(Model model) {
		List<User> users = StreamSupport.stream(userService.findAllUsers().spliterator(), false)
				.sorted(comparator)
				.collect(Collectors.toList());
		model.addAttribute("users", users);
		return "admin/users";
	}

}