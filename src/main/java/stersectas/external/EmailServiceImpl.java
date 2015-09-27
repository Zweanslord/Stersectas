package stersectas.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import stersectas.application.EmailService;

@Service(value = "emailService")
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	// TODO: make sending mail async to prevent long wait times on user side
	@Override
	public void send(SimpleMailMessage email) {
		mailSender.send(email);
	}

}