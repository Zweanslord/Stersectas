package stersectas.domain.game;

import java.util.Optional;

public interface GameRepository {

	Optional<Game> findByGameId(GameId gameId);

	int countByName(Name name);

	Optional<Game> findByName(Name name);

	void delete(Game game);

}