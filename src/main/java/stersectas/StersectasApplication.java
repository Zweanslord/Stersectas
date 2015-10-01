package stersectas;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.util.ArrayUtils;

import stersectas.application.UserService;
import stersectas.configuration.profile.TestingProfile;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement(proxyTargetClass = true)
public class StersectasApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StersectasApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		userService.initializeUser();
		if (hasTestingProfile(context)) {
			userService.initialiseTestUser();
		}

	}

	private static boolean hasTestingProfile(ApplicationContext context) {
		return ArrayUtils.contains(context.getEnvironment().getActiveProfiles(), TestingProfile.PROFILE);
	}

	@Bean
	public Clock clock() {
		return Clock.system(ZoneId.of("UTC"));
	}

}