package stersectas.stub;

import org.springframework.mail.SimpleMailMessage;

import stersectas.application.EmailService;

public class EmailServiceStub implements EmailService {

	@Override
	public void send(SimpleMailMessage email) {
		System.out.println("EmailStub received order to send mail: " + email);
	}

}