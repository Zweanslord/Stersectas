package stersectas.stub;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stersectas.configuration.profile.TestingProfile;

@RequestMapping
@Controller
@TestingProfile
public class TimeTravellingController {

	@Autowired
	private TimeTravellingClock clock;

	@ResponseBody
	@RequestMapping("/stub/showclock")
	public String showTime() {
		return clock.instant().toString();
	}

	@RequestMapping(value = "/stub/updateclock", method = RequestMethod.GET)
	public String updateClock(Model model) {
		model.addAttribute("dateTime", LocalDateTime.now(clock));
		return "/stub/updateclock";
	}

	@RequestMapping(value = "/stub/updateclock", method = RequestMethod.POST)
	public String updateClock(String dateTime) {
		clock.travelThroughTimeToLocalDateTime(dateTime);
		return "redirect:/stub/showclock";
	}
}
