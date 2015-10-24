package stersectas.application.game;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class GameNameAvailableValidator implements ConstraintValidator<GameNameAvailable, String> {

	private final GameService gameService;

	@Autowired
	public GameNameAvailableValidator(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public void initialize(GameNameAvailable constraintAnnotation) {
	}

	@Override
	public boolean isValid(String gameName, ConstraintValidatorContext context) {
		return gameService.isGameNameAvailable(gameName);
	}

}