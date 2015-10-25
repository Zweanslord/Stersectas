package stersectas.domain.game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecruitingGameRepository {

	default GameId nextIdentity() {
		return new GameId(UUID.randomUUID().toString().toUpperCase());
	}

	RecruitingGame save(RecruitingGame recruitingGame);

	Optional<RecruitingGame> findByName(Name name);

	Optional<RecruitingGame> findByGameId(GameId gameId);

	List<RecruitingGame> findAllByOrderByNameAsc();

	void delete(RecruitingGame recruitingGame);

}