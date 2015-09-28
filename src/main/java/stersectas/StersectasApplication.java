package stersectas;

import java.time.Clock;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import stersectas.application.UserService;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class StersectasApplication extends WebMvcConfigurerAdapter {

	private static final String LOCALE_COOKIE_NAME = "Stersectas-language";
	private static final Integer FOUR_WEEKS_IN_SECONDS = 60 * 60 * 24 * 7 * 4;

    public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StersectasApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		userService.initializeUsers();
    }

	@Bean
	public Clock clock() {
		return Clock.system(ZoneId.of("Europe/Amsterdam"));
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		cookieLocaleResolver.setCookieName(LOCALE_COOKIE_NAME);
		cookieLocaleResolver.setCookieMaxAge(FOUR_WEEKS_IN_SECONDS);
		return cookieLocaleResolver;
	}

	/**
	 * Maps the files to use for text messages. Basenames is relative to src/main/resources. Supporting English (_en)
	 * and dutch, which is the default when no messages are found.
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(
				"i18n/common",
				"i18n/user");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

	/**
	 * Allows changing the locale by adding a parameter to the url.
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		final JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		mailSenderImpl.setDefaultEncoding("UTF-8");
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(465);
		mailSenderImpl.setProtocol("smtps");
		mailSenderImpl.setUsername("teststersectas@gmail.com");
		mailSenderImpl.setPassword("zalastra");
		mailSenderImpl.setJavaMailProperties(createJavaMailProperties());
		return mailSenderImpl;
	}

	private Properties createJavaMailProperties() {
		final Properties javaMailProps = new Properties();
		javaMailProps.put("mail.debug", "true");
        javaMailProps.put("mail.smtp.auth", "true");
        javaMailProps.put("mail.smtp.starttls.enable", "true");
		return javaMailProps;
	}
}
