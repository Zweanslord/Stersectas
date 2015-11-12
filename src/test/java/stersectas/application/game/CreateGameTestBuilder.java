package stersectas.application.game;

import lombok.Builder;

public class CreateGameTestBuilder {

	@Builder
	public static CreateGame createGame(
			String name,
			String description,
			int maximumPlayers,
			String masterId) {
		return new CreateGame(
				name,
				description,
				maximumPlayers,
				masterId);
	}

	public static CreateGameBuilder defaultBuilder() {
		return builder()
				.name("Test-game")
				.description("Description")
				.maximumPlayers(4)
				.masterId("0123456789");
	}


}
