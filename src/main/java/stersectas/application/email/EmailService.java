package stersectas.application.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void send(Email email);

}