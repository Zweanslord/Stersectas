package stersectas;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import stersectas.application.EmailService;
import stersectas.application.TokenGenerator;
import stersectas.stub.EmailServiceStub;
import stersectas.stub.TimeTravellingClock;
import stersectas.stub.TokenGeneratorStub;

@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public Clock stubbedClock() {
		return new TimeTravellingClock();
	}

	@Bean
	@Primary
	public EmailService stubbedEmailService() {
		return new EmailServiceStub();
	}

	@Bean
	@Primary
	public TokenGenerator stubbedTokenGenerator() {
		return new TokenGeneratorStub();
	}

}
