package stersectas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import stersectas.application.EmailService;
import stersectas.stub.EmailServiceStub;

@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public EmailService stubbedEmailService() {
		return new EmailServiceStub();
	}

}
