package stersectas.application.game;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import stersectas.BaseIT;
import stersectas.application.validation.AllValidations;

public class CreateGameIT extends BaseIT {

	@Autowired
	private Validator validator;

	@Autowired
	private GameService gameService;

	@Test
	public void validCreateGame() {
		CreateGame createGame = new CreateGame();
		createGame.setName("test-game");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(4);
		createGame.setMasterId("0123456789");

		Set<ConstraintViolation<CreateGame>> constraintViolations = validator
				.validate(createGame, AllValidations.class);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	@Transactional
	public void invalidGameName() {
		CreateGame createGame = new CreateGame();
		createGame.setName("");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(4);
		createGame.setMasterId("0123456789");

		Set<ConstraintViolation<CreateGame>> constraintViolations = validator
				.validate(createGame, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 30", constraintViolations.iterator().next().getMessage());
		assertEquals("", constraintViolations.iterator().next().getInvalidValue());
	}

	@Test
	@Transactional
	public void alreadyTakenGameName() {
		createGameWithName("test-game");

		CreateGame createGame = new CreateGame();
		createGame.setName("test-game");
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(4);
		createGame.setMasterId("0123456789");

		Set<ConstraintViolation<CreateGame>> constraintViolations = validator
				.validate(createGame, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Game name already in use", constraintViolations.iterator().next().getMessage());
	}

	private void createGameWithName(String name) {
		CreateGame createGame = new CreateGame();
		createGame.setName(name);
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId("0123456789");

		gameService.createGame(createGame);
	}

}