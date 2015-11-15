package stersectas.view;

import static org.junit.Assert.assertEquals;

import javax.validation.Validator;

import lombok.val;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import stersectas.BaseIT;
import stersectas.application.game.CreateGameTestBuilder;
import stersectas.application.game.GameService;
import stersectas.application.user.UserService;
import stersectas.application.validation.AllValidations;
import stersectas.domain.game.RecruitingGame;
import stersectas.view.member.game.RenameForm;

public class RenameGameFormIT extends BaseIT {

	@Autowired private GameService gameService;
	@Autowired private UserService userService;

	@Autowired private Validator validator;

	@Test
	public void validGameName() {
		val renameGameForm = new RenameForm();
		renameGameForm.setName("test-game");

		val constraintViolations = validator.validate(renameGameForm, AllValidations.class);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	@Transactional
	public void alreadyTakenGameName() {
		createGameWithName("test-game");

		val renameGameForm = new RenameForm();
		renameGameForm.setName("test-game");

		val constraintViolations = validator.validate(renameGameForm, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Game name already in use", constraintViolations.iterator().next().getMessage());
	}

	private RecruitingGame createGameWithName(String name) {
		userService.initialiseTestUser();
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.masterId(userService.findByUsername("test").getUserId().id())
						.build());

		return gameService.findRecruitingGameByName(name);
	}

}