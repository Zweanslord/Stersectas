package stersectas.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LogoutController {

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
		}
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
