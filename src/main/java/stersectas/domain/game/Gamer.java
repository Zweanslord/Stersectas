package stersectas.domain.game;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.experimental.Accessors;
import stersectas.documentation.HibernateConstructor;

@Entity
@Getter
@Accessors(fluent = true)
public class Gamer {

	@EmbeddedId
	private final GamerId gamerId;

	@ElementCollection
	private List<GameId> organizedGames;

	@HibernateConstructor
	private Gamer() {
		gamerId = new GamerId("INVALID-ID");
	}

	private Gamer(GamerId id) {
		gamerId = id;
		organizedGames = new ArrayList<>();
	}

	public static Gamer create(GamerId id) {
		return new Gamer(id);
	}

	public RecruitingGame createRecruitingGame(GameId gameId,
			Name name,
			Description description,
			MaximumPlayers maximumPlayers) {
		return new RecruitingGame(gameId,
				name,
				description,
				maximumPlayers,
				gamerId);
	}

	public void organizeGame(Game game) {
		organizedGames.add(game.gameId());
	}

}