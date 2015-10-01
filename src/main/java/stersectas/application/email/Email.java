package stersectas.application.email;

public class Email {

	private final String to,
			subject,
			htmlMessage;

	public Email(String to, String subject, String htmlMessage) {
		this.to = to;
		this.subject = subject;
		this.htmlMessage = htmlMessage;
	}

	@Override
	public String toString() {
		return "Email[to=" + to + ",subject=" + subject + ",htmlMessage=" + htmlMessage + "]";
	}

	public String getSubject() {
		return subject;
	}

	public String getTo() {
		return to;
	}

	public String getHtmlMessage() {
		return htmlMessage;
	}
}