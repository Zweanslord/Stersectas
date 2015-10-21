package stersectas.application.game;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CreateGame {

	// add validation for unique name
	@Size(min = 1, max = 30)
	private String name;

	@Size(min = 1, max = 1000)
	private String description;

	@Min(1)
	@Max(8)
	private int maximumPlayers;

	private String masterId;

	public CreateGame() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaximumPlayers() {
		return maximumPlayers;
	}

	public void setMaximumPlayers(int maximumPlayers) {
		this.maximumPlayers = maximumPlayers;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

}