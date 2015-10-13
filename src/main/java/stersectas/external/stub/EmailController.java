package stersectas.external.stub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import stersectas.application.email.Email;
import stersectas.profile.TestingProfile;

@RequestMapping
@Controller
@TestingProfile
public class EmailController {

	@Autowired
	private EmailServiceStub emailStub;

	@RequestMapping("stub/email")
	@ResponseBody
	public Email showLastEmail() {
		return emailStub.getLastEmail();
	}

}
