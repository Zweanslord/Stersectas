package stersectas.stub;

import stersectas.application.Token;
import stersectas.application.TokenGenerator;

public class TokenGeneratorStub implements TokenGenerator {

	@Override
	public Token generateToken() {
		return new Token("test-token");
	}

}