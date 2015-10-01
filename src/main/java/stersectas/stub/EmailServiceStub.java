package stersectas.stub;

import stersectas.application.email.Email;
import stersectas.application.email.EmailService;

public class EmailServiceStub implements EmailService {

	private Email lastEmail = null;

	@Override
	public void send(Email email) {
		System.out.println("EmailStub received order to send mail: " + email);
		lastEmail = email;
	}

	public Email getLastEmail() {
		return lastEmail;
	}

	public void resetLastEmail() {
		lastEmail = null;
	}

}