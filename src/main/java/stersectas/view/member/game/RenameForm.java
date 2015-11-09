package stersectas.view.member.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stersectas.application.game.GameNameAvailable;
import stersectas.application.validation.ExtendedValidations;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RenameForm {

	@GameNameAvailable(groups = ExtendedValidations.class)
	private String name;

}