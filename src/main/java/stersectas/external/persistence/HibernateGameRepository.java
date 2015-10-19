package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.game.Game;
import stersectas.domain.game.GameId;
import stersectas.domain.game.GameRepository;

public interface HibernateGameRepository extends CrudRepository<Game, GameId>,
		GameRepository {

}
