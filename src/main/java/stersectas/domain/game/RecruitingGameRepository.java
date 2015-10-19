package stersectas.domain.game;

import java.util.Optional;
import java.util.UUID;

public interface RecruitingGameRepository {

	default GameId nextIdentity() {
		return new GameId(UUID.randomUUID().toString().toUpperCase());
	}

	public RecruitingGame save(RecruitingGame recruitingGame);

	public Optional<RecruitingGame> findByName(Name name);

	public Optional<RecruitingGame> findByGameId(GameId gameId);

	public void delete(RecruitingGame recruitingGame);

}