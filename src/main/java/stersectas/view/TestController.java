package stersectas.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import stersectas.domain.User;
import stersectas.repositories.TestRepository;

@Controller
@RequestMapping("/")
public class TestController {

	@Autowired
	private TestRepository test;

	@RequestMapping
	public String begin(ModelMap model) {
		if (!test.findByUsername("pietje88").isPresent()) {
			test.save(new User("pietje88", "password"));
		}
		model.put("user", test.findByUsername("pietje88").get());
		return "index";
	}
}
