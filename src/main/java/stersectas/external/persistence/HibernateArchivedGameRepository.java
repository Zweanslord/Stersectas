package stersectas.external.persistence;

import org.springframework.data.repository.CrudRepository;

import stersectas.domain.game.ArchivedGame;
import stersectas.domain.game.ArchivedGameRepository;
import stersectas.domain.game.GameId;

public interface HibernateArchivedGameRepository extends CrudRepository<ArchivedGame, GameId>,
		ArchivedGameRepository {

}