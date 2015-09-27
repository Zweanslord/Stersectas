package stersectas.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class PrivateController {

	@Autowired
	public PrivateController() {
	}

	@RequestMapping("/private/")
	public String privateSection(ModelMap model) {
		return "private/private";
	}
}
