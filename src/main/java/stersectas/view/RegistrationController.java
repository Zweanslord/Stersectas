package stersectas.view;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import stersectas.application.user.RegisterUser;
import stersectas.application.user.UserService;

@Controller
@RequestMapping
public class RegistrationController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registerForm(Model model) {
		model.addAttribute("registerUser", new RegisterUser());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registerUser(@Valid RegisterUser registerUser, BindingResult bindingResult, HttpServletRequest request) {
		if (!bindingResult.hasErrors()) {
			userService.registerNewUser(registerUser, request);
			return "redirect:/registration-complete";
		}
		return "registration";
	}

	@RequestMapping(value = "registration-confirmation", method = RequestMethod.GET)
	public String registrationConfirmation(Model model, @RequestParam("token") String token) {
		boolean confirmed = userService.confirmEmailVerification(token);
		model.addAttribute("tokenError", !confirmed);
		return "registration-confirmation";
	}

	@RequestMapping(value = "/registration-complete", method = RequestMethod.GET)
	public String registrationComplete() {
		return "registration-complete";
	}
}
