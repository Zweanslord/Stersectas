package stersectas.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import stersectas.application.EmailService;

@Service(value = "emailService")
public class JavaMailSenderEmailService implements EmailService {

	private final JavaMailSender mailSender;

	@Autowired
	public JavaMailSenderEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	// TODO: make sending mail async to prevent long wait times on user side
	// TODO: Use MimeMessage rather than SimpleMailMessage
	@Override
	public void send(SimpleMailMessage email) {
		mailSender.send(email);
	}

}