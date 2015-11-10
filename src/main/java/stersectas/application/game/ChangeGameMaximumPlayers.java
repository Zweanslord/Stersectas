package stersectas.application.game;

import lombok.Value;

@Value
public class ChangeGameMaximumPlayers {

	String gameId;
	int maximumPlayers;

}