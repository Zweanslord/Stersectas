package stersectas.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.domain.User;
import stersectas.repositories.UserRepository;

@RequestMapping
@Controller
public class PrivateController {

	private final UserRepository userRepository;

	@Autowired
	public PrivateController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping("/private/")
	public String privateSection(ModelMap model) {
		if (!userRepository.findByUsername("pietje88").isPresent()) {
			userRepository.save(new User("pietje88", "password"));
		}
		model.put("user", userRepository.findByUsername("pietje88").get());
		return "private/private";
	}
}
