package stersectas.application;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	
	void send(SimpleMailMessage email);

}