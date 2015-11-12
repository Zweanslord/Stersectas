package stersectas.view.member.game;

import lombok.Data;
import lombok.NoArgsConstructor;
import stersectas.application.game.GameNameAvailable;
import stersectas.application.validation.ExtendedValidations;

@Data
@NoArgsConstructor
public class CreateGameForm {

	@GameNameAvailable(groups = ExtendedValidations.class)
	private String name;

	private String description;

	private int maximumPlayers = 4;

}
