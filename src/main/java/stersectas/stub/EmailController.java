package stersectas.stub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import stersectas.configuration.profile.TestingProfile;

@RequestMapping
@Controller
@TestingProfile
public class EmailController {

	@Autowired
	private EmailServiceStub emailStub;

	@RequestMapping("stub/email")
	@ResponseBody
	public SimpleMailMessage showLastEmail() {
		return emailStub.getLastEmail();
	}

}
