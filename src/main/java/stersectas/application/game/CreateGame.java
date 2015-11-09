package stersectas.application.game;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import lombok.Data;
import stersectas.application.validation.ExtendedValidations;

@Data
public class CreateGame {

	@Size(min = 1, max = 30)
	@GameNameAvailable(groups = ExtendedValidations.class)
	private String name;

	@Size(min = 1, max = 1000)
	private String description;

	@Min(1)
	@Max(8)
	private int maximumPlayers = 4;

	private String masterId;

}