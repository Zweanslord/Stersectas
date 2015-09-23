package stersectas.view;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import stersectas.application.UserDto;
import stersectas.application.UserService;

@Controller
@RequestMapping
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute("userDto", new UserDto());
		return "registration";
	}

	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String registerUser(@Valid UserDto userDto, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			userService.registerNewUser(userDto);
			return "redirect:/registration-complete";
		}
		return "registration";
	}

	@RequestMapping("/registration-complete")
	public String registrationComplete() {
		return "registration-complete";
	}
}
