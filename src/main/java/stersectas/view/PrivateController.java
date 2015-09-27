package stersectas.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class PrivateController {

	@RequestMapping("/private/")
	public String privateSection(ModelMap model) {
		return "private/private";
	}
}
