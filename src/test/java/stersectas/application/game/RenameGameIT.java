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
import stersectas.domain.game.RecruitingGame;

public class RenameGameIT extends BaseIT {

	@Autowired
	private Validator validator;

	@Autowired
	private GameService gameService;

	@Test
	public void validGameName() {
		RenameGame renameGame = new RenameGame();
		renameGame.setName("test-game");
		renameGame.setGameId("0123456789");

		Set<ConstraintViolation<RenameGame>> constraintViolations = validator
				.validate(renameGame, AllValidations.class);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	@Transactional
	public void invalidGameName() {
		RenameGame renameGame = new RenameGame();
		renameGame.setName("");
		renameGame.setGameId("0123456789");

		Set<ConstraintViolation<RenameGame>> constraintViolations = validator
				.validate(renameGame, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 30", constraintViolations.iterator().next().getMessage());
		assertEquals("", constraintViolations.iterator().next().getInvalidValue());
	}

	@Test
	@Transactional
	public void alreadyTakenGameName() {
		RecruitingGame recruitingGame = createGameWithName("test-game");

		RenameGame renameGame = new RenameGame();
		renameGame.setName("test-game");
		renameGame.setGameId(recruitingGame.gameId().id());

		Set<ConstraintViolation<RenameGame>> constraintViolations = validator
				.validate(renameGame, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Game name already in use", constraintViolations.iterator().next().getMessage());
	}

	private RecruitingGame createGameWithName(String name) {
		CreateGame createGame = new CreateGame();
		createGame.setName(name);
		createGame.setDescription("Description");
		createGame.setMaximumPlayers(2);
		createGame.setMasterId("0123456789");

		gameService.createGame(createGame);

		return gameService.findRecruitingGameByName(name);
	}

}