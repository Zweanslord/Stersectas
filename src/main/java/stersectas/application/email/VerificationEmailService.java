package stersectas.application.email;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import stersectas.application.VerificationToken;
import stersectas.domain.User;

@Service
public class VerificationEmailService {

	private static final Logger log = LoggerFactory.getLogger(VerificationEmailService.class);

	private static final String SUBJECT_MESSAGE_KEY = "email.register.verification";

	private final EmailService emailService;

	private final MessageSource messageSource;

	private final TemplateEngine templateEngine;


	@Autowired
	public VerificationEmailService(
			EmailService emailService,
			MessageSource messageSource,
			TemplateEngine templateEngine) {
		this.emailService = emailService;
		this.messageSource = messageSource;
		this.templateEngine = templateEngine;
	}

	public void sendEmailVerification(User user, VerificationToken verificationToken, HttpServletRequest request) {
		log.info("Sending verification token {} to {} for registration of {}",
				verificationToken.tokenString(), user.getEmail(), user.getUsername());

		String to = user.getEmail();
		String subject = messageSource.getMessage(SUBJECT_MESSAGE_KEY, null, "Registration Confirmation",
				request.getLocale());
		String content = createContent(verificationToken, request);

		emailService.send(new Email(to, subject, content));
	}

	public String createContent(VerificationToken verificationToken, HttpServletRequest request) {
		final WebContext context = new WebContext(request, null, request.getServletContext(), request.getLocale());
		context.setVariable("username", verificationToken.getUser().getUsername());
		context.setVariable("token", verificationToken.tokenString());
		return templateEngine.process("email/verification", context);
	}

}