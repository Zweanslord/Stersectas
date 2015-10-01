package stersectas.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import stersectas.application.EmailService;
import stersectas.configuration.profile.DefaultProfile;

@Service(value = "emailService")
@DefaultProfile
public class JavaMailSenderEmailService implements EmailService {

	private final JavaMailSender mailSender;

	@Autowired
	public JavaMailSenderEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	// TODO: Use MimeMessage rather than SimpleMailMessage
	@Override
	@Async
	public void send(SimpleMailMessage email) {
		mailSender.send(email);
	}

}