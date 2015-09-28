package stersectas.external;

import java.util.UUID;

import org.springframework.stereotype.Service;

import stersectas.application.Token;
import stersectas.application.TokenGenerator;

@Service("TokenGenerator")
public class RandomTokenGenerator implements TokenGenerator {

	@Override
	public Token generateToken() {
		return new Token(UUID.randomUUID().toString());
	}

}