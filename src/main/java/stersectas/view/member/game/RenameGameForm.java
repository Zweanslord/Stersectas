package stersectas.view.member.game;

import stersectas.application.game.GameNameAvailable;
import stersectas.application.validation.ExtendedValidations;

public class RenameGameForm {

	@GameNameAvailable(groups = ExtendedValidations.class)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}