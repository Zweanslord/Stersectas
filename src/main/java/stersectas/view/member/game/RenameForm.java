package stersectas.view.member.game;

import stersectas.application.game.GameNameAvailable;
import stersectas.application.validation.ExtendedValidations;

public class RenameForm {

	@GameNameAvailable(groups = ExtendedValidations.class)
	private String name;

	public RenameForm() {
	}

	public RenameForm(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}