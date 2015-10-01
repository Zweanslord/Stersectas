package stersectas.configuration;

import java.time.Clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import stersectas.application.EmailService;
import stersectas.configuration.profile.TestingProfile;
import stersectas.stub.EmailServiceStub;
import stersectas.stub.TimeTravellingClock;

@Configuration
@TestingProfile
public class StubConfiguration {

	private static final Logger log = LoggerFactory.getLogger(StubConfiguration.class);

	@Bean
	@Primary
	public Clock stubbedClock() {
		log.info("Using timetravelling clock.");
		return new TimeTravellingClock();
	}

	@Bean
	@Primary
	public EmailService stubbedEmailService() {
		return new EmailServiceStub();
	}

}