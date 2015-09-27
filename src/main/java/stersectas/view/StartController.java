package stersectas.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StartController {

	@RequestMapping("/")
	public String start(ModelMap model) {
		return "index";
	}
}
