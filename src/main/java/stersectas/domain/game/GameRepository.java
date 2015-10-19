package stersectas.domain.game;

import java.util.Optional;

public interface GameRepository {

	Optional<Game> findByGameId(GameId gameId);

	void delete(Game game);

}