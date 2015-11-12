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
import stersectas.application.validation.AllValidations;
import stersectas.view.member.game.CreateGameForm;

public class CreateGameFormIT extends BaseIT {

	@Autowired
	private Validator validator;

	@Autowired
	private GameService gameService;

	@Test
	public void validCreateGameForm() {
		val createGameForm = new CreateGameForm();
		createGameForm.setName("test-game");
		createGameForm.setDescription("Description");
		createGameForm.setMaximumPlayers(4);

		val constraintViolations = validator.validate(createGameForm, AllValidations.class);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	@Transactional
	public void alreadyTakenGameName() {
		createGameWithName("test-game");

		val createGameForm = new CreateGameForm();
		createGameForm.setName("test-game");
		createGameForm.setDescription("Description");
		createGameForm.setMaximumPlayers(4);

		val constraintViolations = validator.validate(createGameForm, AllValidations.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Game name already in use", constraintViolations.iterator().next().getMessage());
	}

	private void createGameWithName(String name) {
		gameService.createGame(
				CreateGameTestBuilder.defaultBuilder()
						.name(name)
						.build());
	}

}