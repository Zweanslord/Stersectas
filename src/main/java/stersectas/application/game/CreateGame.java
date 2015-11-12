package stersectas.application.game;

import lombok.Value;

@Value
public class CreateGame {

	String name;
	String description;
	int maximumPlayers;
	String masterId;

}