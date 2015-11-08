package stersectas.view;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import stersectas.BaseIT;
import stersectas.application.game.CreateGame;
import stersectas.application.game.GameService;
import stersectas.application.validation.AllValidations;
import stersectas.domain.game.RecruitingGame;
import stersectas.view.member.game.RenameGameForm;

public class RenameGameFormIT extends BaseIT {

	@Autowired
	private Validator validator;

	@Autowired
	private GameService gameService;

	@Test
	public void validGameName() {
		RenameGameForm renameGameForm = new RenameGameForm();
		renameGameForm.setName("test-game");

		Set<ConstraintViolation<RenameGameForm>> constraintViolations = validator
				.validate(renameGameForm, AllValidations.class);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	@Transactional
	public void alreadyTakenGameName() {
		createGameWithName("test-game");

		RenameGameForm renameGameForm = new RenameGameForm();
		renameGameForm.setName("test-game");

		Set<ConstraintViolation<RenameGameForm>> constraintViolations = validator
				.validate(renameGameForm, AllValidations.class);
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