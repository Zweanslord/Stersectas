package stersectas;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import stersectas.application.EmailService;
import stersectas.application.TokenGenerator;
import stersectas.stub.EmailServiceStub;
import stersectas.stub.TokenGeneratorStub;

@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public Clock stubbedClock() {
		ZoneId zone = ZoneId.of("Europe/Amsterdam");
		Instant fixedInstant = LocalDateTime.parse("2014-03-12T19:36:33").atZone(zone).toInstant();
		return Clock.fixed(fixedInstant, zone);
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
