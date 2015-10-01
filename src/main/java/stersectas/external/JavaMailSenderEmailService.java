package stersectas.external;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import stersectas.application.email.Email;
import stersectas.application.email.EmailService;
import stersectas.configuration.profile.DefaultProfile;

@Service(value = "emailService")
@DefaultProfile
public class JavaMailSenderEmailService implements EmailService {

	private static final Logger log = LoggerFactory.getLogger(JavaMailSenderEmailService.class);

	private final JavaMailSender mailSender;

	@Autowired
	public JavaMailSenderEmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	@Async
	public void send(Email email) {
		final MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			message.setSubject(email.getSubject());
			message.setTo(email.getTo());
			message.setText(email.getHtmlMessage(), true); // true = isHtml
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// catching exception because this is an Async method
			// maybe still throw some kind of error?
			log.error("Failed to send email.", e);
		}
	}

}