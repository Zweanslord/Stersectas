package stersectas.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.domain.User;
import stersectas.repositories.UserRepository;

@Controller
@RequestMapping
public class StartController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String start(ModelMap model) {
		if (!userRepository.findByUsername("pietje88").isPresent()) {
			userRepository.save(new User("pietje88", "password"));
		}
		model.put("user", userRepository.findByUsername("pietje88").get());
		return "index";
	}
}
